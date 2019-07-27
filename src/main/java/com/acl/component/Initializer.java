package com.acl.component;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.ServletContextResource;

import com.anchol.common.component.storage.HttpAccessable;
import com.anchol.common.component.storage.Storage;
import com.anchol.common.framework.spring.SpringContextHolder;

/**
 * Class Name: Initializer<br>
 * Description: 系统初始化程序
 *
 * @author LiLin
 * @version 1.0
 */
public class Initializer {

	private static Logger log = LoggerFactory.getLogger(Initializer.class);

	private String temporaryFolderPath;

	@Autowired
	private Storage storage;

	public void init() throws IOException {
		WebApplicationContext wac = (WebApplicationContext) SpringContextHolder.getApplicationContext();
		ServletContextResource tempResource = new ServletContextResource(wac.getServletContext(), temporaryFolderPath);
		SystemConfig systemConfig = (SystemConfig) wac.getBean("systemConfig");
		try {
			// 设置临时文件夹
			systemConfig.setTempPath(tempResource.getFile().getAbsolutePath());
			if (storage instanceof HttpAccessable) {
				HttpAccessable httpAccessable = (HttpAccessable) storage;
				systemConfig.setFileServBaseUrl(httpAccessable.getBaseUrl());
			} else {
				systemConfig.setFileServBaseUrl(wac.getServletContext().getContextPath());
			}
			// 设置全局参数
			wac.getServletContext().setAttribute(ServletConstans.REQ_CONTEXT_PATH,
					wac.getServletContext().getContextPath());
			// 设置文件路径参数
			// wac.getServletContext().setAttribute(ServletConstans.FILE_SERVER_BASE_URL,
			// systemConfig.getFileServBaseUrl());
			log.debug("文件上传缓存路径："+systemConfig.getTempPath());
			log.debug("文件上传系统访问路径："+systemConfig.getFileServBaseUrl());
			log.debug("短信网关代理是否开启："+("1"==SystemConfig.use_proxy?"是":"否"));
			log.debug("系统访问地址："+SystemConfig.localUrl);
		} catch (IOException e) {
			log.error("初始化系统临时文件夹时发生异常！");
			throw e;
		}
	}

	/**
	 * @param temporaryFolderPath
	 *            the temporaryFolderPath to set
	 */
	public void setTemporaryFolderPath(String temporaryFolderPath) {
		this.temporaryFolderPath = temporaryFolderPath;
	}
}
