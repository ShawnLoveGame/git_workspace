package com.he.exam.webapp.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;


public class HttpClient {   
	
	private static Logger log = LoggerFactory.getLogger(HttpClient.class);
	
	 /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }   
	
	/**
	 * 使用Post方式访问服务器资源
	 * @return
	 * @throws Exception
	 */
	public static String doPost(String urlStr,Map<String,String> pramas) throws Exception{		
		if(urlStr == null){
			new NullPointerException("url is null!");
		}
		//Post方法对象
		HttpPost httpPost = new HttpPost(urlStr);
		
		//传递的参数对象
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();  
//		parameters.add(new BasicNameValuePair("par","request-post")); 
		//如果请求对象需要传递对象，此处用户遍历传入的参数，然后赋给parameters
		if(!CollectionUtils.isEmpty(pramas)){
			Iterator<Entry<String, String>> it = pramas.entrySet().iterator();
			Entry<String, String> entry = null;
			
			while(it.hasNext()){
				entry = it.next();
				parameters.add(new BasicNameValuePair(entry.getKey(),entry.getValue())); 
			}
		}
		//设置请求参数
		HttpEntity entity = new UrlEncodedFormEntity(parameters,"UTF-8");
        httpPost.setEntity(entity);
        
        return client(httpPost);
	}
	
	/**
	 * 使用Get方式访问服务器资源
	 * @return
	 * @throws Exception
	 */
	public static String doGet(String urlStr,Map<String,String> pramas) throws Exception {       
        if(urlStr == null) {
            new NullPointerException("url is null!");
        }
        
        //Get请求对象
        HttpGet httpGet = new HttpGet(getUrlAddPramas(urlStr, pramas));
        return client(httpGet);
    }
	
	/**
	 * 使用Get方式访问服务器资源，返回图片
	 * @return
	 * @throws Exception
	 */
	public static File doGetPic(String urlStr,Map<String,String> pramas,String urlPath) throws Exception {       
        if(urlStr == null) {
            new NullPointerException("url is null!");
        }
        
        //Get请求对象
        HttpGet httpGet = new HttpGet(getUrlAddPramas(urlStr, pramas));
        return clientPic(httpGet,urlPath);
    }
	
	/**
	 * 接收一个HttpRequestBase（一般为，HttpPost和HttpGet）对象，然后向服务器发送请求，获取资源，返回一个完整的XML文件
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String client(HttpRequestBase request) throws Exception{
	    
		CloseableHttpClient httpclient = HttpClientBuilder.create().build();
		CloseableHttpResponse response = null;
        
        try{
        	response = httpclient.execute(request);
        	
        	//设置请求和传输超时时间
        	RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).build();
        	request.setConfig(requestConfig);
        	
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            	HttpEntity entity = response.getEntity();
            	if (entity != null) {
    				return EntityUtils.toString(entity,"UTF-8");
    			}
            }
        }catch (ClientProtocolException e) {
			e.printStackTrace();
			throw e;
		} catch (ParseException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e){
        	e.printStackTrace();
            throw e;
        } finally {
			// 关闭连接,释放资源
			try {
				if (response != null) {
					response.close();
				}

				if (httpclient != null) {
					httpclient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				throw e;
			}
		}
        
        return null;
	}
	
	/**
	 * 接收一个HttpRequestBase（一般为，HttpPost和HttpGet）对象，然后向服务器发送请求，获取资源，返回一个完整的XML文件
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static File clientPic(HttpRequestBase request,String urlPath) throws Exception{
	    
		CloseableHttpClient httpclient = HttpClientBuilder.create().build();
		CloseableHttpResponse response = null;
        
		File f = new File(urlPath);
		
        try{
        	response = httpclient.execute(request);
        	
        	//设置请求和传输超时时间
        	RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).build();
        	request.setConfig(requestConfig);
        	
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            	HttpEntity entity = response.getEntity();
            	InputStream is =entity.getContent();
            	inputstreamtofile(is, f);
            }
        }catch (ClientProtocolException e) {
			e.printStackTrace();
			throw e;
		} catch (ParseException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e){
        	e.printStackTrace();
            throw e;
        } finally {
			// 关闭连接,释放资源
			try {
				if (response != null) {
					response.close();
				}
				if (httpclient != null) {
					httpclient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				throw e;
			}
		}
        return null;
	}
	
	public static void inputstreamtofile(InputStream ins,File file) throws IOException{
		OutputStream os = new FileOutputStream(file);
		int bytesRead = 0;
		byte[] buffer = new byte[8192];
		while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
			os.write(buffer, 0, bytesRead);
		}
		os.close();
		ins.close();
		}
	
	/**
	 * 将传入的参数组织成一个字符串
	 * @param url
	 * @param pramasMap
	 * @return
	 */
	public static String getUrlAddPramas(String url,Map<String,String> params){
	    StringBuffer sb = new StringBuffer();
	    if(params == null || params.isEmpty()){
            return url;
        }
	    sb.append(url + "?");
	    Iterator<Entry<String, String>> it = params.entrySet().iterator();
        Entry<String, String> entry = null;
        while(it.hasNext()){
            entry = it.next();
            sb.append(entry.getKey()+"="+entry.getValue()+"&"); 
        }
        String result = sb.toString();
	    return result.substring(0,result.length() -1);
	}
}
