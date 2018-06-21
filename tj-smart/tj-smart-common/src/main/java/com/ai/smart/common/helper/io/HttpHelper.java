package com.ai.smart.common.helper.io;

import com.ai.smart.common.base.GlobalResponse;
import com.ai.smart.common.helper.json.JsonHelper;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Class Name : HttpHelper<br>
 * 
 * Description : HTTP 工具类<br>
 * 
 * @author daizy
 * @see
 *
 */
@Slf4j
public class HttpHelper {

	private static int connectionTimeOut = 20000;

	private static int socketTimeOut = 20000;

	private static int maxConnectionPerHost = 500;

	private static int maxTotalConnections = 20000;

	private static CloseableHttpClient httpClient;

	private static CloseableHttpClient httpSmsClient;

	@SuppressWarnings("unused")
	private static CloseableHttpAsyncClient asyncClient;


	private static int retryTime = 3;

	static {
		try {
			HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
			RequestConfig.Builder requestConfig = RequestConfig.custom()
					.setConnectTimeout(connectionTimeOut)
					.setConnectionRequestTimeout(connectionTimeOut)
					.setSocketTimeout(socketTimeOut);
			httpClientBuilder.setDefaultRequestConfig(requestConfig.build());
			ConnectionKeepAliveStrategy myStrategy = new ConnectionKeepAliveStrategy() {
				@Override
				public long getKeepAliveDuration(HttpResponse response,
						HttpContext context) {
					return 65 * 1000;
				}
			};
			httpClientBuilder.setKeepAliveStrategy(myStrategy);
			httpClient = httpClientBuilder.build();
			// 重试机制
			HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler(){
				public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
					if (executionCount >= retryTime) {
						return false;
					}
					// 服务端断掉客户端的连接异常
					if (exception instanceof NoHttpResponseException) {
						return true;
					}
					// time out 超时重试
					if (exception instanceof InterruptedIOException) {
						return true;
					}
					// Unknown host
					if (exception instanceof UnknownHostException) {
						return false;
					}
					// Connection refused
					if (exception instanceof ConnectTimeoutException) {
						return false;
					}
					// SSL handshake exception
					if (exception instanceof SSLException) {
						return false;
					}
					HttpClientContext clientContext = HttpClientContext.adapt(context);
					HttpRequest request = clientContext.getRequest();
					if (!(request instanceof HttpEntityEnclosingRequest)) {
						return true;
					}
					return false;
				}
			};

			httpClientBuilder.setRetryHandler(httpRequestRetryHandler);
			httpSmsClient = httpClientBuilder.build();
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

		} catch (IOReactorException e) {
			log.error("URLConnectionHelper is error ",
					getStackTrace(e));
		}
	}

	private static String delete(String url, Map<String, String> paramMap,
			ContentType contentType, RequestConfig configuration) {
		HttpRequestBase httpDelete = null;
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		int statusCode = 0;
		String responseBodyAsString = "";
		try {
			if (MapUtils.isNotEmpty(paramMap)) {
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				for (Entry<String, String> entry : paramMap.entrySet()) {
					nvps.add(new BasicNameValuePair(entry.getKey(), entry
							.getValue()));
				}
				url = new StringBuilder(url).append("?")
						.append(URLEncodedUtils.format(nvps, Consts.UTF_8))
						.toString();
			}
			if (log.isDebugEnabled()) {
				log.debug("\n\n\n%%%%%%%%%%%%%%%http send: " + url
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
				responseBodyAsString = EntityUtils.toString(entity,
						Consts.UTF_8);
			}
			if (log.isDebugEnabled()) {
				log.debug("\n%%%%%%%%%%%%%%%http state: " + statusCode
						+ "\n%%%%%%%%%%%%%%%response:" + responseBodyAsString);
			}
		} catch (Exception e) {
			log.error("soa exception   %%%%% http url:" + url);
			log.error(getStackTrace(e));
		} finally {
			close(entity, httpDelete, response);
		}
		return responseBodyAsString;
	}

	private static String get(String url, Map<String, String> paramMap,
			ContentType contentType, RequestConfig configuration) {
		HttpRequestBase httpGet = null;
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		int statusCode = 0;
		String responseBodyAsString = "";
		try {
			if (MapUtils.isNotEmpty(paramMap)) {
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				for (Entry<String, String> entry : paramMap.entrySet()) {
					nvps.add(new BasicNameValuePair(entry.getKey(), entry
							.getValue()));
				}
				url = new StringBuilder(url).append("?")
						.append(URLEncodedUtils.format(nvps, Consts.UTF_8))
						.toString();
			}
			if (log.isDebugEnabled()) {
				log.debug("\n\n\n%%%%%%%%%%%%%%%http send: " + url
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
				responseBodyAsString = EntityUtils.toString(entity,
						Consts.UTF_8);
			}
			if (log.isDebugEnabled()) {
				log.debug("\n%%%%%%%%%%%%%%%http state: " + statusCode
						+ "\n%%%%%%%%%%%%%%%response:" + responseBodyAsString);
			}
		} catch (Exception e) {
			log.error("soa exception   %%%%% http url:" + url);
			log.error(getStackTrace(e));
		} finally {
			close(entity, httpGet, response);
		}
		return responseBodyAsString;
	}

	private static String put(String url, Map<String, String> paramMap,
			String json, ContentType contentType, RequestConfig configuration) {
		HttpEntityEnclosingRequestBase httpPut = null;
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		int statusCode = 0;
		String responseBodyAsString = "";
		try {
			httpPut = new HttpPut(url);
			httpPut.addHeader(HTTP.CONTENT_TYPE, contentType.toString());
			// 将表单的值放入postMethod中
			StringEntity se = new StringEntity("");
			if (MapUtils.isNotEmpty(paramMap)) {
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				for (Entry<String, String> entry : paramMap.entrySet()) {
					nvps.add(new BasicNameValuePair(entry.getKey(), entry
							.getValue()));
				}
				se = new UrlEncodedFormEntity(nvps, Consts.UTF_8);
			} else if (!Strings.isNullOrEmpty(json)) {
				se = new StringEntity(json, Consts.UTF_8);
			}
			httpPut.setEntity(se);
			if (log.isDebugEnabled()) {
				log.debug("\n\n\n%%%%%%%%%%%%%%%http send: " + url
						+ "\n%%%%%%%%%%%%%%%paramMap:" + paramMap);
			}
			if (configuration != null) {
				httpPut.setConfig(configuration);
			}
			response = httpClient.execute(httpPut);
			entity = response.getEntity();
			statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				responseBodyAsString = EntityUtils.toString(entity,
						Consts.UTF_8);
				;
			}
			if (log.isDebugEnabled()) {
				log.debug("\n%%%%%%%%%%%%%%%http state: " + statusCode
						+ "\n%%%%%%%%%%%%%%%response:" + responseBodyAsString);
			}
		} catch (Exception e) {
			log.error("soa exception   %%%%% http url:" + url);
			log.error(getStackTrace(e));
		} finally {
			close(entity, httpPut, response);
		}
		return responseBodyAsString;
	}

	@SuppressWarnings("unchecked")
	private static String post(String url, Map<String, String> paramMap,
			String json, ContentType contentType, RequestConfig configuration) {
		HttpEntityEnclosingRequestBase httpPost = null;
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		int statusCode = 0;
		String responseBodyAsString = "";
		try {
			httpPost = new HttpPost(url);
			httpPost.addHeader(HTTP.CONTENT_TYPE, contentType.toString());
			// 将表单的值放入postMethod中
			StringEntity se = new StringEntity("");
			if (MapUtils.isNotEmpty(paramMap)) {
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				for (Entry<String, String> entry : paramMap.entrySet()) {
					nvps.add(new BasicNameValuePair(entry.getKey(), entry
							.getValue()));
				}
				se = new UrlEncodedFormEntity(nvps, Consts.UTF_8);
				httpPost.setEntity(se);
			} else if (!Strings.isNullOrEmpty(json)) {
				Map<String, Object> map = JsonHelper.fromJson(json, Map.class);
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				for (Entry<String, Object> entry : map.entrySet()) {
					nvps.add(new BasicNameValuePair(entry.getKey(), String
							.valueOf(entry.getValue())));
				}
				se = new UrlEncodedFormEntity(nvps, Consts.UTF_8);
				httpPost.setEntity(se);
			}
			if (log.isDebugEnabled()) {
				log.debug("\n\n\n%%%%%%%%%%%%%%%http send: " + url
						+ "\n%%%%%%%%%%%%%%%paramMap:" + paramMap
						+ "\n%%%%%%%%%%%%%%%json:" + json);
			}
			if (configuration != null) {
				httpPost.setConfig(configuration);
			}
			response = httpClient.execute(httpPost);
			entity = response.getEntity();
			statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				responseBodyAsString = EntityUtils.toString(entity,
						Consts.UTF_8);
				;
			}
			if (log.isDebugEnabled()) {
				log.debug("\n%%%%%%%%%%%%%%%http state: " + statusCode
						+ "\n%%%%%%%%%%%%%%%response:" + responseBodyAsString);
			}
		} catch (Exception e) {
			log.error("soa exception   %%%%% http url:" + url);
			log.error(getStackTrace(e));
		} finally {
			close(entity, httpPost, response);
		}
		return responseBodyAsString;
	}

	/**
	 * post请求
	 * 
	 * @param url
	 * @param param
	 * @return
	 */
	public static String postStr(String url, String param) {
		HttpEntityEnclosingRequestBase httpPost = null;
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		int statusCode = 0;
		String responseBodyAsString = "";
		try {
			httpPost = new HttpPost(url);
			httpPost.addHeader(
					HTTP.CONTENT_TYPE,
					ContentType.APPLICATION_FORM_URLENCODED.withCharset(
							Consts.UTF_8).toString());
			// 将表单的值放入postMethod中

			StringEntity se = new StringEntity(param.toString(), "utf-8");
			httpPost.setEntity(se);
			response = httpClient.execute(httpPost);
			entity = response.getEntity();
			statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				responseBodyAsString = EntityUtils.toString(entity,
						Consts.UTF_8);
				;
			}
		} catch (Exception e) {
			log.error("soa exception   %%%%% http url:" + url);
			log.error(getStackTrace(e));
		} finally {
			close(entity, httpPost, response);
		}
		return responseBodyAsString;
	}

	public static GlobalResponse postJson(String url, String json) {
		HttpEntityEnclosingRequestBase httpPost = null;
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		int statusCode = 0;
		String responseBodyAsString = "";
		try {
			httpPost = new HttpPost(url);
			httpPost.addHeader(
					HTTP.CONTENT_TYPE,
					ContentType.APPLICATION_FORM_URLENCODED.withCharset(
							Consts.UTF_8).toString());
			// 将表单的值放入postMethod中

			httpPost.setEntity(doStringEntity(json));
			response = httpClient.execute(httpPost);
			entity = response.getEntity();
			statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				responseBodyAsString = EntityUtils.toString(entity,
						Consts.UTF_8);
				return JsonHelper.fromJson(responseBodyAsString,GlobalResponse.class);
			}
		} catch (Exception e) {
			log.error("soa exception   %%%%% http url:" + url);
			log.error(getStackTrace(e));
		} finally {
			close(entity, httpPost, response);
		}
		return GlobalResponse.fail(responseBodyAsString);
	}

	public static StringEntity doStringEntity(String json) throws  Exception{
        StringEntity se = new StringEntity("");
        if (!Strings.isNullOrEmpty(json)) {
            Map<String, Object> map = JsonHelper.fromJson(json, Map.class);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            Map<String, String> paramMap = Maps.newHashMap();
            for (Entry<String, Object> entry : map.entrySet()) {
                String entryKey = entry.getKey();
                String entryVal = String.valueOf(entry.getValue());
                paramMap.put(entryKey, entryVal);
                nvps.add(new BasicNameValuePair(entryKey ,entryVal));
            }
            map.clear();
            paramMap.clear();
            se = new UrlEncodedFormEntity(nvps, Consts.UTF_8);
        }
        return se;
    }

	public static GlobalResponse postAppJson(String url, String json, String header) {
		HttpEntityEnclosingRequestBase httpPost = null;
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		int statusCode = 0;
		String responseBodyAsString = "";
		try {
			httpPost = new HttpPost(url);
			httpPost.addHeader(
					HTTP.CONTENT_TYPE,
					ContentType.APPLICATION_FORM_URLENCODED.withCharset(
							Consts.UTF_8).toString());
			httpPost.addHeader("Authorization",header);
			// 将表单的值放入postMethod中

			httpPost.setEntity(doStringEntity(json));
			response = httpClient.execute(httpPost);
			entity = response.getEntity();
			statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				responseBodyAsString = EntityUtils.toString(entity,
						Consts.UTF_8);
				return JsonHelper.fromJson(responseBodyAsString,GlobalResponse.class);
			}
		} catch (Exception e) {
			log.error("soa exception   %%%%% http url:" + url);
			log.error(getStackTrace(e));
		} finally {
			close(entity, httpPost, response);
		}
		return GlobalResponse.fail(responseBodyAsString);
	}
	public static GlobalResponse postAppJson(String url, String json, String header,ContentType type) {
		HttpEntityEnclosingRequestBase httpPost = null;
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		int statusCode = 0;
		String responseBodyAsString = "";
		try {
			httpPost = new HttpPost(url);
			httpPost.addHeader(
					HTTP.CONTENT_TYPE,
					type.withCharset(
							Consts.UTF_8).toString());
			httpPost.addHeader("Authorization",header);
			// 将表单的值放入postMethod中

			httpPost.setEntity(doStringEntity(json));
			response = httpClient.execute(httpPost);
			entity = response.getEntity();
			statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				responseBodyAsString = EntityUtils.toString(entity,
						Consts.UTF_8);
				return JsonHelper.fromJson(responseBodyAsString,GlobalResponse.class);
			}
		} catch (Exception e) {
			log.error("soa exception   %%%%% http url:" + url);
			log.error(getStackTrace(e));
		} finally {
			close(entity, httpPost, response);
		}
		return GlobalResponse.fail(responseBodyAsString);
	}
	public static GlobalResponse postSmsJson(String url, String json) {
        String responseBodyAsString = "";
		try {
            CloseableHttpResponse response = null;
            HttpUriRequest httpUriRequest = RequestBuilder
                    .post()
                    .setHeader(
                            new BasicHeader(HttpHeaders.CONTENT_TYPE,
                                    ContentType.APPLICATION_JSON.toString()))
                    .setUri(url)
                    .setEntity(doStringEntity(json))
                    .build();

            HttpEntity entity = null;
            int statusCode = 0;
			response = httpSmsClient.execute(httpUriRequest);
			entity = response.getEntity();
			statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				responseBodyAsString = EntityUtils.toString(entity,
						Consts.UTF_8);
				return JsonHelper.fromJson(responseBodyAsString,GlobalResponse.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GlobalResponse.fail(responseBodyAsString);
	}

	public static String postWithForm(String url, Map<String, String> paramMap,
			Charset charset, RequestConfig configuration) {
		return post(url, paramMap, null,
				ContentType.APPLICATION_FORM_URLENCODED.withCharset(charset),
				configuration);
	}

	public static String postWithForm(String url, Map<String, String> paramMap,
			RequestConfig configuration) {
		return postWithForm(url, paramMap, Consts.UTF_8, configuration);
	}

	public static String postWithForm(String url, Map<String, String> paramMap,
			Charset charset) {
		return postWithForm(url, paramMap, charset, null);
	}

	public static String postWithForm(String url, Map<String, String> paramMap) {
		return postWithForm(url, paramMap, Consts.UTF_8, null);
	}

	public static String getWithForm(String url, Map<String, String> paramMap) {
		return get(url, paramMap,
				ContentType.APPLICATION_FORM_URLENCODED
						.withCharset(Consts.UTF_8), null);
	}

	public static String deleteWithForm(String url, Map<String, String> paramMap) {
		return delete(url, paramMap,
				ContentType.APPLICATION_FORM_URLENCODED
						.withCharset(Consts.UTF_8), null);
	}

	public static String putWithForm(String url, Map<String, String> paramMap) {
		return put(url, paramMap, null,
				ContentType.APPLICATION_FORM_URLENCODED
						.withCharset(Consts.UTF_8), null);
	}

	public static String postWithJSON(String url, String json,
			RequestConfig configuration) {
		return post(url, null, json,
				ContentType.APPLICATION_FORM_URLENCODED
						.withCharset(Consts.UTF_8), configuration);
	}

	public static String postWithJSON(String url, String json) {
		return postWithJSON(url, json, null);
	}

	private static void close(HttpEntity entity, HttpRequestBase request,
			CloseableHttpResponse response) {
		try {
			if (request != null)
				request.releaseConnection();
			if (entity != null)
				EntityUtils.consume(entity);
			if (response != null)
				response.close();
		} catch (IllegalStateException e) {
			log.error(getStackTrace(e));
		} catch (IOException e) {
			log.error(getStackTrace(e));
		}
	}
	
	public static String getStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        e.printStackTrace(pw);
        return sw.getBuffer().toString();
    }
}
