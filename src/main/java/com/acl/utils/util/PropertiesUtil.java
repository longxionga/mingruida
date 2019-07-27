package com.acl.utils.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import java.io.*;
import java.net.URL;
import java.util.Properties;

/**
 * @Title PropertiesUtils.java
 *
 * @Package com.acl.utils
 *
 * @author GuJiale
 *
 * @Company ANCHOL
 *
 * @Description 配置文件读取
 *
 *              2015年12月21日下午6:55:54
 *
 */
public class PropertiesUtil {

	private String filename;
	private Properties p;
	private FileInputStream in;
	private FileOutputStream out;

	public PropertiesUtil() {
		super();
		URL url = this.getClass().getClassLoader().getResource("spring_mvc/config.properties");
		this.filename = url.getFile();
		File file = new File(this.filename);
		try {
			in = new FileInputStream(file);
			p = new Properties();
			// 解决中文乱码问题
			p.load(new InputStreamReader(in, "UTF-8"));
			in.close();
		} catch (FileNotFoundException e) {
			LogUtils.error("配置文件config.properties找不到！");
		} catch (IOException e) {
			LogUtils.error("读取配置文件config.properties错误！");
		}
	}

	public PropertiesUtil(String filename) {
		this.filename = filename;
		File file = new File(filename);
		try {
			in = new FileInputStream(file);
			p = new Properties();
			p.load(new InputStreamReader(in, "UTF-8"));
			in.close();
		} catch (FileNotFoundException e) {
			LogUtils.error("配置文件config.properties找不到！");
			e.printStackTrace();
		} catch (IOException e) {
			LogUtils.error("读取配置文件config.properties错误！");
			e.printStackTrace();
		}
	}

	public static String getConfigFile(HttpServlet hs) {
		return getConfigFile(hs, "config.properties");
	}

	private static String getConfigFile(HttpServlet hs, String configFileName) {
		String configFile = "";
		ServletContext sc = hs.getServletContext();
		configFile = sc.getRealPath("/" + configFileName);
		if (configFile == null || configFile.equals("")) {
			configFile = "/" + configFileName;
		}

		return configFile;
	}

	public void list() {
		p.list(System.out);
	}

	public String getValue(String itemName) {
		return p.getProperty(itemName);
	}

	public String getValue(String itemName, String defaultValue) {
		return p.getProperty(itemName, defaultValue);
	}

	public void setValue(String itemName, String value) {
		p.setProperty(itemName, value);
	}

	public void saveFile(String filename, String description) throws Exception {
		try {
			File f = new File(filename);
			out = new FileOutputStream(f);
			p.store(out, description);
			out.close();
		} catch (IOException ex) {
			throw new Exception("无法保存指定的配置文件:" + filename);
		}
	}

	public void saveFile(String filename) throws Exception {
		saveFile(filename, "");
	}

	/**
	 * 保存文件
	 * 
	 * @throws Exception
	 */
	public void saveFile() throws Exception {
		if (filename.length() == 0) {
			throw new Exception("需指定保存的配置文件名");
		}

		saveFile(filename);
	}

	/**
	 * 删除值
	 * 
	 * @param value
	 */
	public void deleteValue(String value) {
		p.remove(value);
	}

	public static String readValue(String filePath, String key) {
		Properties props = new Properties();
		try {
			InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream(filePath);
			props.load(in);
			String value = props.getProperty(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String resolvePath(String param) {
		String path = PropertiesUtil.readValue("config.properties", param);
		return path;
	}

	public static String dataBasePath(String param) {
		String path = PropertiesUtil.readValue("/conf/database.properties", param);
		return path;
	}
	
	public static String keycert(String param) {
		String path = PropertiesUtil.readValue("/spring_mvc/keycert.properties", param);
		return path;
	}

}
