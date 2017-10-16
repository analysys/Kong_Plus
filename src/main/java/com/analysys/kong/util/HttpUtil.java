package com.analysys.kong.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.node.ObjectNode;

/**
 * HTTP客户端
 * @author Administrator
 */
public final class HttpUtil {
	private static final int TIMEOUT = 1000 * 30;
	
	/**
	 * 发送GET请求
	 * @param url
	 * @param headers
	 * @return
	 */
	public static String get(String url, Map<String, String> headers) {
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		try {
			HttpGet httpget = new HttpGet(url);
			for (String key : headers.keySet()) {
				httpget.addHeader(key, headers.get(key));
			}
			RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.BEST_MATCH).setExpectContinueEnabled(true).setConnectTimeout(TIMEOUT).setSocketTimeout(TIMEOUT).setStaleConnectionCheckEnabled(true).build();
			httpget.setConfig(config);
			CookieStore cookieStore = new BasicCookieStore();
			//cookieStore.addCookie(null);
			httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).setDefaultCredentialsProvider(new BasicCredentialsProvider()).build();
			response = httpclient.execute(httpget);
			String result = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
			return result;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				if (httpclient != null) {
					httpclient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	/**
	 * 发送POST请求
	 * @param url
	 * @param headers
	 * @param params
	 * @return
	 */
	public static String post(String url, Map<String, String> headers, Map<String, String> params) {
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		try {
			HttpPost httpPost = new HttpPost(url);
			for (String key : headers.keySet()) {
				httpPost.addHeader(key, headers.get(key));
			}
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			for (String key : params.keySet()) {
				nvps.add(new BasicNameValuePair(key, params.get(key)));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			CookieStore cookieStore = new BasicCookieStore();
			//cookieStore.addCookie(null);
			httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
			response = httpclient.execute(httpPost);
			String result = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
			return result;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				if (httpclient != null) {
					httpclient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	
	public static String postJson(String url, Map<String, String> headers, ObjectNode dataNode) {
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		try {
			HttpPost httpPost = new HttpPost(url);
			for (String key : headers.keySet()) {
				httpPost.addHeader(key, headers.get(key));
			}
			StringEntity entity = new StringEntity(dataNode.toString(), "UTF-8");
			httpPost.setHeader("Content-Type", "application/json");
			httpPost.setEntity(entity);
			CookieStore cookieStore = new BasicCookieStore();
			//cookieStore.addCookie(null);
			httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
			response = httpclient.execute(httpPost);
			String result = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
			return result;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				if (httpclient != null) {
					httpclient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	
	/**
	 * 发送PUT请求
	 * @param url
	 * @param headers
	 * @param params
	 * @return
	 */
	public static String put(String url, Map<String, String> headers, Map<String, String> params) {
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		try {
			HttpPut httpPut = new HttpPut(url);
			for (String key : headers.keySet()) {
				httpPut.addHeader(key, headers.get(key));
			}
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			for (String key : params.keySet()) {
				nvps.add(new BasicNameValuePair(key, params.get(key)));
			}
			httpPut.setEntity(new UrlEncodedFormEntity(nvps));
			CookieStore cookieStore = new BasicCookieStore();
			//cookieStore.addCookie(null);
			httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
			response = httpclient.execute(httpPut);
			String result = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
			return result;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				if (httpclient != null) {
					httpclient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	public static String putJson(String url, Map<String, String> headers, ObjectNode dataNode) {
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		try {
			HttpPut httpPut = new HttpPut(url);
			for (String key : headers.keySet()) {
				httpPut.addHeader(key, headers.get(key));
			}
			StringEntity entity = new StringEntity(dataNode.toString(), "UTF-8");
			httpPut.setHeader("Content-Type", "application/json");
			httpPut.setEntity(entity);
			CookieStore cookieStore = new BasicCookieStore();
			//cookieStore.addCookie(null);
			httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
			response = httpclient.execute(httpPut);
			String result = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
			return result;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				if (httpclient != null) {
					httpclient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	
	/**
	 * 发送PATCH请求
	 * @param url
	 * @param headers
	 * @param params
	 * @return
	 */
	public static String patch(String url, Map<String, String> headers, Map<String, String> params) {
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		try {
			HttpPatch httpPatch = new HttpPatch(url);
			for (String key : headers.keySet()) {
				httpPatch.addHeader(key, headers.get(key));
			}
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			for (String key : params.keySet()) {
				nvps.add(new BasicNameValuePair(key, params.get(key)));
			}
			httpPatch.setEntity(new UrlEncodedFormEntity(nvps));
			CookieStore cookieStore = new BasicCookieStore();
			//cookieStore.addCookie(null);
			httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
			response = httpclient.execute(httpPatch);
			String result = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
			return result;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				if (httpclient != null) {
					httpclient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	
	/**
	 * 发送DELETE请求
	 * @param url
	 * @param headers
	 * @return
	 */
	public static String delete(String url, Map<String, String> headers) {
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		try {
			HttpDelete httpDelete = new HttpDelete(url);
			for (String key : headers.keySet()) {
				httpDelete.addHeader(key, headers.get(key));
			}
			CookieStore cookieStore = new BasicCookieStore();
			//cookieStore.addCookie(null);
			httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
			response = httpclient.execute(httpDelete);
			return "";
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				if (httpclient != null) {
					httpclient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
}
