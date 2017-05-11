package com.he.exam.controller.ueditor;

import com.he.exam.model.pan.SizeVO;
import com.he.exam.ueditor.ConfigManager;
import com.he.exam.ueditor.define.*;
import com.he.exam.ueditor.hunter.FileManager;
import com.he.exam.ueditor.hunter.ImageHunter;
import com.he.exam.util.FtpUploader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
public class UeditorCotroller {
	
	private Log log = LogFactory.getLog(getClass());
	
	private ConfigManager configManager = null;

	@Value("${ftpHost}") private String ftp_ip;
	@Value("${ftpPort}") private String ftp_port;

	@Value("${ftpServerName}") private String ftp_user;
	@Value("${ftpServerPwd}") private String ftp_pwd;

	@Value("${ftp_path}") private String ftp_path;
	

	@ResponseBody
	@RequestMapping(value = "/editor/config")
	public String globalController(@RequestParam(value = "action") String actionType, HttpServletRequest request) throws Exception {
		configManager = ConfigManager.getInstance(request.getSession().getServletContext().getRealPath("/"), request.getContextPath(), request.getRequestURI());
		if (actionType == null || !ActionMap.mapping.containsKey(actionType)) {
			return new BaseState(false, AppInfo.INVALID_ACTION).toJSONString();
		}
		if (configManager == null || !this.configManager.valid()) {
			return new BaseState(false, AppInfo.CONFIG_ERROR).toJSONString();
		}
		State state = null;
		int actionCode = ActionMap.getType(actionType);
		Map<String, Object> conf = null;
		switch (actionCode) {
		case ActionMap.CONFIG: return configManager.getAllConfig().toString();
		case ActionMap.UPLOAD_IMAGE:
		case ActionMap.UPLOAD_SCRAWL:
		case ActionMap.UPLOAD_VIDEO:
		case ActionMap.UPLOAD_FILE:
			conf = configManager.getConfig(actionCode);
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile upfile = multipartRequest.getFile((String) conf.get("fieldName"));
			
			if (upfile == null) {
				state = new BaseState(false, AppInfo.INVALID_ACTION);
			} else {
				String suffix = FileType.getSuffixByFilename(upfile.getOriginalFilename());
				
				//文件类型检查
				if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
					state = new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
					break;
				}
				
				//文件大小检查
				long maxSize = ((Long) conf.get("maxSize")).longValue();
				if (upfile.getSize() > maxSize) {
					state = new BaseState(false, AppInfo.MAX_SIZE);
					break;
				}
				String path = request.getSession().getServletContext().getRealPath("upload"); 
				File targetFile = new File(path, upfile.getOriginalFilename());
				if(!targetFile.exists()){  
		            targetFile.mkdirs();  
		        }  
				upfile.transferTo(targetFile);
				String videoName = upfile.getOriginalFilename();
				String profix = videoName.substring(videoName.lastIndexOf("."));
//				String uploadFilePath = System.getProperty("java.io.tmpdir");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				String localName = sdf.format(new Date()) + profix;
				//判断是否是图片
				//".png", ".jpg", ".jpeg", ".gif", ".bmp"
				FtpUploader.FTPUpload(ftp_ip, Integer.valueOf(ftp_port), ftp_user, ftp_pwd, localName, targetFile, ftp_path);
				String url="http://"+ftp_ip+ftp_path+"/"+localName;
				targetFile.delete();
				state = new BaseState(true);
				if (null != url) {
					state.putInfo("url",url);
					state.putInfo("type", "");
					state.putInfo("original", upfile.getOriginalFilename());
				} else {
					state = new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
				}
			}
			break;

		case ActionMap.CATCH_IMAGE:
			conf = configManager.getConfig(actionCode);
			String[] list = request.getParameterValues((String) conf.get("fieldName"));
			List<Map<String,String>> rs = new ImageHunter(conf).captureRemote(list);
			MultiState states = new MultiState( true );
			for(Map<String,String> mp : rs){
				File targetFile = new File(mp.get("url"));
				List<SizeVO> sizeList = new ArrayList<SizeVO>();
				sizeList.add(new SizeVO(640,400));

				String profix = targetFile.getName().substring(targetFile.getName().lastIndexOf("."));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				String localName = sdf.format(new Date()) + profix;
				//判断是否是图片
				//".png", ".jpg", ".jpeg", ".gif", ".bmp"
				FtpUploader.FTPUpload(ftp_ip, Integer.valueOf(ftp_port), ftp_user, ftp_pwd, localName, targetFile, ftp_path);
				String url="http://"+ftp_ip+ftp_path+"/"+localName;

				log.error("远程抓取图片上传："+url);
				targetFile.delete();
				State stat = new BaseState(true);
				//判断原图大小  100k 左右大小
				stat.putInfo( "url", url);
				stat.putInfo( "source", mp.get("source") );

				stat.putInfo("type", "");
				stat.putInfo("original", targetFile.getName());
				states.addState( stat );
			}
			state = states;
			break;

		case ActionMap.LIST_IMAGE:
		case ActionMap.LIST_FILE:
			conf = configManager.getConfig(actionCode);
			int start = 0;
			try {
				start = Integer.parseInt(request.getParameter("start"));
			} catch (Exception e) {
			}
			state = new FileManager(conf).listFile(start);
			break;

		}
		return state.toJSONString();
	}
	
	
	private boolean validType(String type, String[] allowTypes) {
		List<String> list = Arrays.asList(allowTypes);
		return list.contains(type);
	}


}
