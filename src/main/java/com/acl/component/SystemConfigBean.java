package com.acl.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Class Name: SystemConfig<br>
 * Description: 系统配置参数
 *
 * @author lilin
 * @version 1.0
 */
public class SystemConfigBean {
    // 系统默认密码
    @Value("${AncholPWD}")
    public String AncholPWD;
    // 是否通过
    @Value("${keyMy}")
    public String keyMy;
    // 是否通过代理发送短信 线上为1 开发测试为0

    @Value("${use_proxy}")
    public String use_proxy;
    @Value("${proxy_ip}")
    public String proxy_ip;
    @Value("${proxy_port}")
    public String proxy_port;
    // 地址
    @Value("${localUrl}")
    public String localUrl;
    //图片地址
    @Value("${imgUrl}")
    public String imgUrl;
    @Value("${vsimgUrl}")
    public String vsimgUrl;
    @Value("${zfUrl}")
    public String zfUrl;
    @Value("${strnumber}")
    public String strnumber;
    @Value("${strletter}")
    public String strletter;
    @Value("${strnumandlet}")
    public String strnumandlet;


    public String getAncholPWD() {
        return AncholPWD;
    }

    public void setAncholPWD(String ancholPWD) {
        AncholPWD = ancholPWD;
    }


    public String getKeyMy() {
        return keyMy;
    }

    public void setKeyMy(String keyMy) {
        this.keyMy = keyMy;
    }

    public String getUse_proxy() {
        return use_proxy;
    }

    public void setUse_proxy(String use_proxy) {
        this.use_proxy = use_proxy;
    }

    public String getProxy_ip() {return proxy_ip;}

    public void setProxy_ip(String proxy_ip) {this.proxy_ip = proxy_ip;}

    public String getProxy_port() {return proxy_port;}

    public void setProxy_port(String proxy_port) {this.proxy_port = proxy_port;}

    public String getLocalUrl() {
        return localUrl;
    }

    public void setLocalUrl(String localUrl) {
        this.localUrl = localUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getVsimgUrl() {
        return vsimgUrl;
    }

    public void setVsimgUrl(String vsimgUrl) {
        this.vsimgUrl = vsimgUrl;
    }

    public String getZfUrl() {
        return zfUrl;
    }

    public void setZfUrl(String zfUrl) {
        this.zfUrl = zfUrl;
    }

    public String getStrnumber() {
        return strnumber;
    }

    public void setStrnumber(String strnumber) {
        this.strnumber = strnumber;
    }

    public String getStrletter() {
        return strletter;
    }

    public void setStrletter(String strletter) {
        this.strletter = strletter;
    }

    public String getStrnumandlet() {
        return strnumandlet;
    }

    public void setStrnumandlet(String strnumandlet) {
        this.strnumandlet = strnumandlet;
    }

}
