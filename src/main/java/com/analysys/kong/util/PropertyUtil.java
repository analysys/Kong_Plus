package com.analysys.kong.util;

import java.io.FileInputStream;
import java.net.URLDecoder;
import java.util.Properties;

public class PropertyUtil {
	private static Properties config = null;
	private static final String PROPERTYIES_NAME = "conf.properties";

	private PropertyUtil() {
		config = new Properties();
		try {
			String path = this.getClass().getClassLoader().getResource("").getPath();
			path = URLDecoder.decode(path, "utf-8");
			config.load(new FileInputStream(path + PROPERTYIES_NAME));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static class LazyHolder {
		private static PropertyUtil INSTANCE = new PropertyUtil();
	}

	public static PropertyUtil getInstance() {
		return LazyHolder.INSTANCE;
	}

	public String getString(String key) {
		return config.getProperty(key);
	}
	
	public String getString(String key, String defaultVal) {
		return config.getProperty(key, defaultVal);
	}
}