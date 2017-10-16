package com.analysys.kong.util;

/**
 * @author Administrator
 */
public class SystemParam {
	public static final String ROWS = "rows";
	public static final String TOTAL = "total";
	public static final String PAGE = "page";
	public static final Integer PAGENUM = 11;
	public static final String VALID = "1";
	public static final String INVALID = "0";
	public static final String keyAuth = "key";
	public static final String basicAuth = "basic";
	public static final String oAuth2 = "oauth2";
	public static String KONG_SERVER_API_ADMIN = "";
	public static String KONG_SERVER_API_VISIT = "";
	public static String KONG_API_LOG_FILE = "";
	public static String KONG_API_LOG_HTTP = "";
	public static String KONG_API_CALL_SURPLUS = "";
	
	static {
		KONG_SERVER_API_ADMIN = PropertyUtil.getInstance().getString("kong.server.api.admin", "http://127.0.0.1:8001");
		KONG_SERVER_API_VISIT = PropertyUtil.getInstance().getString("kong.server.api.visit", "http://127.0.0.1:8000");
		KONG_API_LOG_FILE = PropertyUtil.getInstance().getString("kong.api.log.file", "/data/kong/access.log");
		KONG_API_LOG_HTTP = PropertyUtil.getInstance().getString("kong.api.log.http", "http://127.0.0.1:8097/api-gateway/apilogs/receive");
		KONG_API_CALL_SURPLUS = PropertyUtil.getInstance().getString("kong.api.callcount.surplus", "100");
	}
}
