package com.he.exam.ueditor.hunter;

import com.he.exam.ueditor.PathFormat;
import com.he.exam.ueditor.define.*;
import com.he.exam.ueditor.upload.StorageManager;
import org.springframework.util.CollectionUtils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * 图片抓取器
 * @author hancong03@baidu.com
 *
 */
public class ImageHunter {

	private String filename = null;
	private String savePath = null;
	private String rootPath = null;
	private List<String> allowTypes = null;
	private long maxSize = -1;
	
	private List<String> filters = null;
	
	public ImageHunter ( Map<String, Object> conf ) {
		
		this.filename = (String)conf.get( "filename" );
		this.savePath = (String)conf.get( "savePath" );
		this.rootPath = (String)conf.get( "rootPath" );
		this.maxSize = (Long)conf.get( "maxSize" );
		this.allowTypes = Arrays.asList( (String[])conf.get( "allowFiles" ) );
		this.filters = Arrays.asList( (String[])conf.get( "filter" ) );
		
	}
	
	public State capture ( String[] list ) {
		
		MultiState state = new MultiState( true );
		
		for ( String source : list ) {
			state.addState( captureRemoteData( source ) );
		}
		
		return state;
		
	}

	public State captureRemoteData ( String urlStr ) {
		
		HttpURLConnection connection = null;
		URL url = null;
		String suffix = null;
		
		try {
			url = new URL( urlStr );

			if ( !validHost( url.getHost() ) ) {
				return new BaseState( false, AppInfo.PREVENT_HOST );
			}
			
			connection = (HttpURLConnection) url.openConnection();
		
			connection.setInstanceFollowRedirects( true );
			connection.setUseCaches( true );
		
			if ( !validContentState( connection.getResponseCode() ) ) {
				return new BaseState( false, AppInfo.CONNECTION_ERROR );
			}
			
			suffix = MIMEType.getSuffix( connection.getContentType() );
			
			if ( !validFileType( suffix ) ) {
				return new BaseState( false, AppInfo.NOT_ALLOW_FILE_TYPE );
			}
			
			if ( !validFileSize( connection.getContentLength() ) ) {
				return new BaseState( false, AppInfo.MAX_SIZE );
			}
			
			String savePath = this.getPath( this.savePath, this.filename, suffix );
			String physicalPath = this.rootPath + savePath;

			State state = StorageManager.saveFileByInputStream( connection.getInputStream(), physicalPath );
			
			if ( state.isSuccess() ) {
				state.putInfo( "url", PathFormat.format( savePath ) );
				state.putInfo( "source", urlStr );
			}
			
			return state;
			
		} catch ( Exception e ) {
			return new BaseState( false, AppInfo.REMOTE_FAIL );
		}
		
	}
	
	private String getPath ( String savePath, String filename, String suffix  ) {
		
		return PathFormat.parse( savePath + suffix, filename );
		
	}
	
	private boolean validHost ( String hostname ) {
		
		return !filters.contains( hostname );
		
	}
	
	private boolean validContentState ( int code ) {
		
		return HttpURLConnection.HTTP_OK == code;
		
	}
	
	private boolean validFileType ( String type ) {
		
		return this.allowTypes.contains( type );
		
	}
	
	private boolean validFileSize ( int size ) {
		return size < this.maxSize;
	}
	
	
	public List<Map<String,String>> captureRemote ( String[] list ) {
		List<Map<String,String>>rs = new ArrayList<Map<String,String>>();
		for ( String source : list ) {
			//判断是否是微信图片
//			http://mmbiz.qpic.cn/mmbiz/tjgs0v0FzkkYmW0xPoJyJYG6QU75vr2ou4y8Ff2te4cFL4Eu1HXWCBqfj0cSjQaic2MLv0laNoY9bwQtEvopupw/640?wx_fmt=jpeg&wxfrom=5&wx_lazy=1
			if(source.contains("?wx_fmt")){
				String str = source.substring(source.indexOf("?wx_fmt=") , source.length());
				String fix_type = str.split("&")[0].split("=")[1];
				source = source.substring(0, source.indexOf("?wx_fmt")) + "." + fix_type;
			}
			Map<String,String> mp = captureRemoteDataNew( source );
			if(!CollectionUtils.isEmpty(mp)){
				rs.add(mp);
			}
		}
		return rs;
	}

	public Map<String,String> captureRemoteDataNew ( String urlStr ) {
		Map<String,String> rs = new HashMap<String, String>();
		HttpURLConnection connection = null;
		URL url = null;
		String suffix = null;
		try {
			url = new URL( urlStr );
			if ( !validHost( url.getHost() ) ) {
				rs.put( "url", "");
				rs.put("source", urlStr);
			}
			connection = (HttpURLConnection) url.openConnection();
		
			connection.setInstanceFollowRedirects( true );
			connection.setUseCaches( true );
		
			if ( !validContentState( connection.getResponseCode() ) ) {
				rs.put( "url", "");
				rs.put("source", urlStr);
			}
			
			suffix = MIMEType.getSuffix( connection.getContentType() );
			
			if ( !validFileType( suffix ) ) {
				rs.put( "url", "");
				rs.put("source", urlStr);
			}
			
			if ( !validFileSize( connection.getContentLength() ) ) {
				rs.put( "url", "");
				rs.put("source", urlStr);
			}
			
			String savePath = this.getPath( this.savePath, this.filename, suffix );
			String physicalPath = this.rootPath + savePath;

			State state = StorageManager.saveFileByInputStream( connection.getInputStream(), physicalPath );
			
			if ( state.isSuccess() ) {
//				state.putInfo( "url", PathFormat.format( savePath ) );
//				state.putInfo( "source", urlStr );
				rs.put( "url", physicalPath);
				rs.put("source", urlStr);
			}else{
				rs.put( "url", "");
				rs.put("source", urlStr);
			}
			
			return rs;
			
		} catch ( Exception e ) {
			return null;
		}
		
	}
}
