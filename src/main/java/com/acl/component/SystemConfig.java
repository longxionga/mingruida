package com.acl.component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;

/**
 * Class Name: SystemConfig<br>
 * Description: 系统配置参数
 *
 * @author lilin
 * @version 1.0
 */
public class SystemConfig {

    // 文件服务器HTTP访问基路径
    private String fileServBaseUrl;
    // 系统临时文件夹
    private String tempPath;
    // 系统默认密码
    public static String AncholPWD;
    // 是否通过
    public static String keyMy;
    // 是否通过代理发送短信 线上为1 开发测试为0
    public static String use_proxy;

    public static String proxy_ip;  //代理IP

    public static String proxy_port; //代理端口
    // 地址
    public static String localUrl;
    //图片地址
    public static String imgUrl;
    public static String vsimgUrl;
    public static String strnumber;
    public static String strletter;
    public static String strnumandlet;

    @Resource
    SystemConfigBean systemConfigBean;

    @PostConstruct
    public void init() {
        // 系统默认密码
        AncholPWD = systemConfigBean.getAncholPWD();
        // 是否通过
        keyMy = systemConfigBean.getKeyMy();
        // 是否通过代理发送短信 线上为1 开发测试为0
        use_proxy = systemConfigBean.getUse_proxy();
        //代理IP
        proxy_ip = systemConfigBean.getProxy_ip();

        //代理端口
        proxy_port = systemConfigBean.getProxy_port();

        // 地址
        localUrl = systemConfigBean.getLocalUrl();
        //图片地址
        imgUrl = systemConfigBean.getImgUrl();
        vsimgUrl = systemConfigBean.getVsimgUrl();
        strnumber = systemConfigBean.getStrnumber();
        strletter = systemConfigBean.getStrletter();
        strnumandlet = systemConfigBean.getStrnumandlet();

    
    }


    /**
     * @return the tempPath
     */
    public String getTempPath() {
        return tempPath;
    }

    /**
     * @param tempPath the tempPath to set
     */
    public void setTempPath(String tempPath) {
        this.tempPath = tempPath;
    }

    /**
     * @return the fileServBaseUrl
     */
    public String getFileServBaseUrl() {
        return fileServBaseUrl;
    }

    /**
     * @param fileServBaseUrl the fileServBaseUrl to set
     */
    public void setFileServBaseUrl(String fileServBaseUrl) {
        this.fileServBaseUrl = fileServBaseUrl;
    }
}
