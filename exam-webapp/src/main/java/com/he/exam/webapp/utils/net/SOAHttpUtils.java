/**
 * 
 */
package com.he.exam.webapp.utils.net;

import com.he.exam.webapp.utils.json.JsonUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.config.*;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author liuhb
 * @version $Revision$
 * @see
 *
 */
public class SOAHttpUtils {

    private static final Logger logger = LoggerFactory.getLogger(SOAHttpUtils.class);

    private static int connectionTimeOut = 20000;

    private static int socketTimeOut = 20000;

    private static int maxConnectionPerHost = 500;

    private static int maxTotalConnections = 10000;

    private static CloseableHttpClient     httpClient;
    
    @SuppressWarnings("unused")
    private static CloseableHttpAsyncClient asyncClient;
    
    static {

        try {
            SSLContext sslContext = SSLContexts.custom().build();
            sslContext.init(null, new TrustManager[] { new X509TrustManager() {

                public void checkClientTrusted(X509Certificate[] arg0, String arg1)
                        throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] arg0, String arg1)
                        throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            } }, null);

            //X509HostnameVerifier hostnameVerifier = new AllowAllHostnameVerifier();
            NoopHostnameVerifier noopHostnameVerifier = new NoopHostnameVerifier();
            
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
                    .<ConnectionSocketFactory> create().register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", new SSLConnectionSocketFactory(sslContext,noopHostnameVerifier)).build();
            PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).setSoKeepAlive(true).build();
            connManager.setDefaultSocketConfig(socketConfig);
            MessageConstraints messageConstraints = MessageConstraints.custom().setMaxHeaderCount(200)
                    .setMaxLineLength(2000).build();
            ConnectionConfig connectionConfig = ConnectionConfig.custom()
                    .setMalformedInputAction(CodingErrorAction.IGNORE)
                    .setUnmappableInputAction(CodingErrorAction.IGNORE).setCharset(Consts.UTF_8)
                    .setMessageConstraints(messageConstraints).build();
            connManager.setDefaultConnectionConfig(connectionConfig);
            connManager.setMaxTotal(200);
            connManager.setDefaultMaxPerRoute(20);

            HttpClientBuilder httpClientBuilder = HttpClients.custom();

            httpClientBuilder.setConnectionManager(connManager);

            RequestConfig.Builder requestConfig = RequestConfig.custom()
                    .setConnectTimeout(connectionTimeOut)
                    .setConnectionRequestTimeout(connectionTimeOut)
                    .setSocketTimeout(socketTimeOut);

            httpClientBuilder.setDefaultRequestConfig(requestConfig.build());

            ConnectionKeepAliveStrategy myStrategy = new ConnectionKeepAliveStrategy() {

                public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                    return 65 * 1000;
                }
            };

            httpClientBuilder.setKeepAliveStrategy(myStrategy);
            httpClient = httpClientBuilder.build();
            asyncClient = HttpAsyncClients.createDefault();
            ConnectingIOReactor ioReactor = new DefaultConnectingIOReactor();
            PoolingNHttpClientConnectionManager cm = new PoolingNHttpClientConnectionManager(
                    ioReactor);
            cm.setMaxTotal(maxTotalConnections);
            cm.setDefaultMaxPerRoute(maxConnectionPerHost);
            RequestConfig asyncrequestConfig = RequestConfig.custom()
                    .setSocketTimeout(socketTimeOut)
                    .setConnectTimeout(connectionTimeOut).build();
            asyncClient = HttpAsyncClients.custom().setConnectionManager(cm)
                    .setDefaultRequestConfig(asyncrequestConfig).build();
            //asyncClient.start();
            
        } catch (KeyManagementException e) {
            logger.error("URLConnectionHelper is error ",ExceptionUtils.getStackTrace(e));
        } catch (NoSuchAlgorithmException e) {
            logger.error("URLConnectionHelper is error ", ExceptionUtils.getStackTrace(e));
        } catch (IOReactorException e) {
            logger.error("URLConnectionHelper is error ", ExceptionUtils.getStackTrace(e));
        }
    }
    
    private static String delete(String url, Map<String, String> paramMap,ContentType contentType,RequestConfig configuration) {
        HttpRequestBase httpDelete = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        int statusCode = 0;
        String responseBodyAsString = StringUtils.EMPTY;
        try {
            if (MapUtils.isNotEmpty(paramMap)) {
                List<NameValuePair> nvps = new ArrayList <NameValuePair>();
                for (Entry<String, String> entry : paramMap.entrySet()) {
                    nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                url  = new StringBuilder(url).append("?").append(URLEncodedUtils.format(nvps, Consts.UTF_8)).toString();
            } 
            if (logger.isDebugEnabled()) {
                logger.debug("\n\n\n%%%%%%%%%%%%%%%http send: " + url
                        + "\n%%%%%%%%%%%%%%%paramMap:" + paramMap);
            }
            httpDelete = new HttpDelete(url);
            httpDelete.addHeader(HTTP.CONTENT_TYPE, contentType.toString());
            if (configuration != null) {
                httpDelete.setConfig(configuration);
            } 
            response = httpClient.execute(httpDelete);
            entity = response.getEntity();
            statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                responseBodyAsString = EntityUtils.toString(entity,Consts.UTF_8);
            }
            if (logger.isDebugEnabled()) {
                logger.debug("\n%%%%%%%%%%%%%%%http state: " + statusCode
                        + "\n%%%%%%%%%%%%%%%response:" + responseBodyAsString);
            }
        } catch (Exception e) {
            logger.error("soa exception   %%%%% http url:" + url);
            logger.error(ExceptionUtils.getStackTrace(e));
        } finally {
            close(entity,httpDelete,response);
        }
        return responseBodyAsString;
    }
    
    private static String get(String url, Map<String, String> paramMap,ContentType contentType,RequestConfig configuration) {
        HttpRequestBase httpGet = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        int statusCode = 0;
        String responseBodyAsString = StringUtils.EMPTY;
        try {
            if (MapUtils.isNotEmpty(paramMap)) {
                List<NameValuePair> nvps = new ArrayList <NameValuePair>();
                for (Entry<String, String> entry : paramMap.entrySet()) {
                    nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                url  = new StringBuilder(url).append("?").append(URLEncodedUtils.format(nvps, Consts.UTF_8)).toString();
            } 
            if (logger.isDebugEnabled()) {
                logger.debug("\n\n\n%%%%%%%%%%%%%%%http send: " + url
                        + "\n%%%%%%%%%%%%%%%paramMap:" + paramMap);
            }
            httpGet = new HttpGet(url);
            httpGet.addHeader(HTTP.CONTENT_TYPE, contentType.toString());
            if (configuration != null) {
                httpGet.setConfig(configuration);
            } 
            response = httpClient.execute(httpGet);
            entity = response.getEntity();
            statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                responseBodyAsString = EntityUtils.toString(entity,Consts.UTF_8);
            }
            if (logger.isDebugEnabled()) {
                logger.debug("\n%%%%%%%%%%%%%%%http state: " + statusCode
                        + "\n%%%%%%%%%%%%%%%response:" + responseBodyAsString);
            }
        } catch (Exception e) {
            logger.error("soa exception   %%%%% http url:" + url);
            logger.error(ExceptionUtils.getStackTrace(e));
        } finally {
            close(entity,httpGet,response);
        }
        return responseBodyAsString;
    }
    
	private static String put(String url, Map<String, String> paramMap,String json,ContentType contentType,RequestConfig configuration) {
        HttpEntityEnclosingRequestBase httpPut = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        int statusCode = 0;
        String responseBodyAsString = StringUtils.EMPTY;
        try {
            httpPut = new HttpPut(url);
            httpPut.addHeader(HTTP.CONTENT_TYPE, contentType.toString());
            // 将表单的值放入postMethod中
            StringEntity se = new StringEntity(StringUtils.EMPTY);
            if (MapUtils.isNotEmpty(paramMap)) {
                List<NameValuePair> nvps = new ArrayList <NameValuePair>();
                for (Entry<String, String> entry : paramMap.entrySet()) {
                    nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                se = new UrlEncodedFormEntity(nvps,Consts.UTF_8);
            } else if (StringUtils.isNotBlank(json)) {
                se = new StringEntity(json,Consts.UTF_8);
            }
            httpPut.setEntity(se); 
            if (logger.isDebugEnabled()) {
                logger.debug("\n\n\n%%%%%%%%%%%%%%%http send: " + url
                        + "\n%%%%%%%%%%%%%%%paramMap:" + paramMap);
            }
            if (configuration != null) {
                httpPut.setConfig(configuration);
            } 
            response = httpClient.execute(httpPut);
            entity = response.getEntity();
            statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                responseBodyAsString = EntityUtils.toString(entity,Consts.UTF_8);;
            }
            if (logger.isDebugEnabled()) {
                logger.debug("\n%%%%%%%%%%%%%%%http state: " + statusCode
                        + "\n%%%%%%%%%%%%%%%response:" + responseBodyAsString);
            }
        } catch (Exception e) {
            logger.error("soa exception   %%%%% http url:" + url);
            logger.error(ExceptionUtils.getStackTrace(e));
        } finally {
            close(entity,httpPut,response);
        }
        return responseBodyAsString;
    }
    
	@SuppressWarnings("unchecked")
	private static String post(String url, Map<String, String> paramMap,String json,ContentType contentType,RequestConfig configuration) {
        HttpEntityEnclosingRequestBase httpPost = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        int statusCode = 0;
        String responseBodyAsString = StringUtils.EMPTY;
        try {
            httpPost = new HttpPost(url);
            httpPost.addHeader(HTTP.CONTENT_TYPE, contentType.toString());
            // 将表单的值放入postMethod中
            StringEntity se = new StringEntity(StringUtils.EMPTY);
            if (MapUtils.isNotEmpty(paramMap)) {
                List<NameValuePair> nvps = new ArrayList <NameValuePair>();
                for (Entry<String, String> entry : paramMap.entrySet()) {
                    nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                se = new UrlEncodedFormEntity(nvps,Consts.UTF_8);
                httpPost.setEntity(se); 
            } else if (StringUtils.isNotBlank(json)) {
            	Map<String, Object> map = JsonUtils.fromJson(json, Map.class);
            	 List<NameValuePair> nvps = new ArrayList <NameValuePair>();
                 for (Entry<String, Object> entry : map.entrySet()) {
                     nvps.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
                 }
                 se = new UrlEncodedFormEntity(nvps,Consts.UTF_8);
                 httpPost.setEntity(se); 
            }
            if (logger.isDebugEnabled()) {
                logger.debug("\n\n\n%%%%%%%%%%%%%%%http send: " + url
                        + "\n%%%%%%%%%%%%%%%paramMap:" + paramMap
                        +"\n%%%%%%%%%%%%%%%json:"+json);
            }
            if (configuration != null) {
                httpPost.setConfig(configuration);
            } 
            response = httpClient.execute(httpPost);
            entity = response.getEntity();
            statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                responseBodyAsString = EntityUtils.toString(entity,Consts.UTF_8);;
            }
            if (logger.isDebugEnabled()) {
                logger.debug("\n%%%%%%%%%%%%%%%http state: " + statusCode
                        + "\n%%%%%%%%%%%%%%%response:" + responseBodyAsString);
            }
        } catch (Exception e) {
            logger.error("soa exception   %%%%% http url:" + url);
            logger.error(ExceptionUtils.getStackTrace(e));
        } finally {
            close(entity,httpPost,response);
        }
        return responseBodyAsString;
    }
	
	/**
	 * post请求
	 * @param url
	 * @param param
	 * @return
	 */
	public static String postStr(String url,String param) {
        HttpEntityEnclosingRequestBase httpPost = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        int statusCode = 0;
        String responseBodyAsString = StringUtils.EMPTY;
        try {
            httpPost = new HttpPost(url);
            httpPost.addHeader(HTTP.CONTENT_TYPE, ContentType.APPLICATION_FORM_URLENCODED.withCharset(Consts.UTF_8).toString());
            // 将表单的值放入postMethod中
            
            StringEntity se = new StringEntity(param.toString(), "utf-8");
            httpPost.setEntity(se);
            response = httpClient.execute(httpPost);
            entity = response.getEntity();
            statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                responseBodyAsString = EntityUtils.toString(entity,Consts.UTF_8);;
            }
        } catch (Exception e) {
            logger.error("soa exception   %%%%% http url:" + url);
            logger.error(ExceptionUtils.getStackTrace(e));
        } finally {
            close(entity,httpPost,response);
        }
        return responseBodyAsString;
    }
	
	
	public static String postJson(String url,String param) {
		CloseableHttpResponse response = null;
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setHeader(new BasicHeader(HttpHeaders.CONTENT_TYPE,ContentType.APPLICATION_JSON.toString()))
				.setUri(url)
				.setEntity(new StringEntity(param,Charset.forName("utf-8")))
				.build();
		String responseBodyAsString = StringUtils.EMPTY;
		HttpEntity entity = null;
	    int statusCode = 0;
		try {
			response = httpClient.execute(httpUriRequest);
			entity = response.getEntity();
            statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                responseBodyAsString = EntityUtils.toString(entity,Consts.UTF_8);;
            }
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return responseBodyAsString;
    }
	
    
    public static String postWithForm(String url, Map<String, String> paramMap,Charset charset,RequestConfig configuration) {
        return post(url,paramMap,null,ContentType.APPLICATION_FORM_URLENCODED.withCharset(charset),configuration);
    }
    
    public static String postWithForm(String url, Map<String, String> paramMap,RequestConfig configuration) {
        return postWithForm(url,paramMap,Consts.UTF_8,configuration);
    }

    public static String postWithForm(String url, Map<String, String> paramMap,Charset charset) {
        return postWithForm(url,paramMap,charset,null);
    }
    
    public static String postWithForm(String url, Map<String, String> paramMap) {
        return postWithForm(url,paramMap,Consts.UTF_8,null);
    }
    
    public static String getWithForm(String url, Map<String, String> paramMap) {
        return get(url,paramMap,ContentType.APPLICATION_FORM_URLENCODED.withCharset(Consts.UTF_8),null);
    }

    public static String deleteWithForm(String url, Map<String, String> paramMap) {
        return delete(url,paramMap,ContentType.APPLICATION_FORM_URLENCODED.withCharset(Consts.UTF_8),null);
    }
    
    public static String putWithForm(String url, Map<String, String> paramMap) {
        return put(url,paramMap,null,ContentType.APPLICATION_FORM_URLENCODED.withCharset(Consts.UTF_8),null);
    }
    
    public static String postWithJSON(String url, String json,RequestConfig configuration) {
        return post(url,null,json,ContentType.APPLICATION_FORM_URLENCODED.withCharset(Consts.UTF_8),configuration);
    }
    
    public static String postWithJSON(String url, String json) {
        return postWithJSON(url,json,null);
    }
    
    private static void close(HttpEntity entity, HttpRequestBase request, CloseableHttpResponse response) {
        try {
            if (request != null)
                request.releaseConnection();
            if (entity != null)
                EntityUtils.consume(entity);
            if (response != null)
                response.close();
        } catch (IllegalStateException e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        } catch (IOException e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }
    }

    public static void main(String[] args) {
    	String withForm1 = postJson("http://127.0.0.1:8080/wxMessage/messageTemplateSend","{\"data\":{\"first\":{\"color\":\"#cccccc\",\"value\":\"aa\"}},\"template_id\":\"c5R6R35UGJPINesjDAhU6EqHT2GSEzDlU_H7u0ovXi8\",\"touser\":\"o8St5xCp-zzSoenWjKAT3qQiQZ2o\",\"url\":\"\"}");
        System.out.println(withForm1);
        
        
       
    }
}
