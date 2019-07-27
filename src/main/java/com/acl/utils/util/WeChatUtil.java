package com.acl.utils.util;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.security.KeyStore;
import javax.net.ssl.SSLContext;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.acl.component.SystemConfig;

@SuppressWarnings("deprecation")
public class WeChatUtil {
	public static DefaultHttpClient httpclient;
	
	private static HttpParams httpParams; 
	
	
	private static ClientConnectionManager connectionManager; 
	
	/**  
     * 最大连接数  
     */    
    public final static int MAX_TOTAL_CONNECTIONS = 800;    
    /**  
     * 获取连接的最大等待时间  
     */    
    public final static int WAIT_TIMEOUT = 60000;    
    /**  
     * 每个路由最大连接数  
     */    
    public final static int MAX_ROUTE_CONNECTIONS = 400;    
    /**  
     * 连接超时时间  
     */    
    public final static int CONNECT_TIMEOUT = 10000;    
    /**  
     * 读取超时时间  
     */    
    public final static int READ_TIMEOUT = 10000;


	//代理IP
	private final static String proxy_ip = SystemConfig.proxy_ip;

	//代理端口
	private final static String proxy_port = SystemConfig.proxy_port;
	
	static {
		httpParams = new BasicHttpParams();    
        // 设置最大连接数    
        ConnManagerParams.setMaxTotalConnections(httpParams, MAX_TOTAL_CONNECTIONS);    
        // 设置获取连接的最大等待时间    
        ConnManagerParams.setTimeout(httpParams, WAIT_TIMEOUT);    
        // 设置每个路由最大连接数    
        ConnPerRouteBean connPerRoute = new ConnPerRouteBean(MAX_ROUTE_CONNECTIONS);    
        ConnManagerParams.setMaxConnectionsPerRoute(httpParams,connPerRoute);    
        // 设置连接超时时间    
        HttpConnectionParams.setConnectionTimeout(httpParams, CONNECT_TIMEOUT);    
        // 设置读取超时时间    
        HttpConnectionParams.setSoTimeout(httpParams, READ_TIMEOUT);    
    
        SchemeRegistry registry = new SchemeRegistry();    
        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));    
        registry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));    
    
        connectionManager = new ThreadSafeClientConnManager(httpParams, registry); 
		
		httpclient = new DefaultHttpClient(connectionManager, httpParams);
		httpclient = (DefaultHttpClient) HttpClientConnectionManager.getSSLInstance(httpclient); // 接受任何证书的浏览器客户端
	}
	
	

	public static String urlEnodeUTF8(String str) {
		String result = str;
		try {
			result = URLEncoder.encode(str, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//微支付
	public static String getWeiZhiFuCorpTransferOrderNo(String requestXml,String entrance,String mchid){
		String url="";
		if (entrance.equals("") || entrance.equals("1")) {
			url="1/apiclient_cert.p12";
		}else{
			url=entrance+"/apiclient_cert.p12";
		}
		try {
			KeyStore keyStore  = KeyStore.getInstance("PKCS12");
		    FileInputStream instream = new FileInputStream(new File(url));//P12文件目录
			try {
	            keyStore.load(instream, mchid.toCharArray());//这里写密码..默认是你的MCHID
	        } finally {
	            instream.close();
	        }
	      
	        // Trust own CA and all self-signed certs
	        SSLContext sslcontext = SSLContexts.custom()
	                .loadKeyMaterial(keyStore, mchid.toCharArray())//这里也是写密码的,..默认是你的MCHID
	                .build();
	        // Allow TLSv1 protocol only
	        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
	                sslcontext,
	                new String[] { "TLSv1" },
	                null,
	                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
	        CloseableHttpClient httpclient = HttpClients.custom()
	                .setSSLSocketFactory(sslsf)
	                .build();
			final String OPENAPI_ADDRESS = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
			final String CHARSET = "UTF-8";
			HttpPost post = new HttpPost(OPENAPI_ADDRESS);
			//String proxyUrl = "10.26.98.138";
			//int proxyPoint = 3128;
			RequestConfig defaultRequestConfig = RequestConfig.custom().
					setSocketTimeout(5000).setConnectTimeout(5000).setMaxRedirects(5000).
					setConnectionRequestTimeout(5000).setStaleConnectionCheckEnabled(true).build();
			RequestConfig requestConfig = RequestConfig.copy(defaultRequestConfig)
					.setProxy(new HttpHost(proxy_ip, Integer.valueOf(proxy_port)))
					.build();
			post.setConfig(requestConfig);
			post.setEntity(new StringEntity(requestXml, ContentType.create(
					"application/x-www-form-urlencoded", CHARSET)));
			HttpResponse response = httpclient.execute(post);
			HttpEntity entity = response.getEntity();
			String body = EntityUtils.toString(entity, CHARSET);
			return body;
		} catch (Exception e) {
			throw new IllegalStateException("通讯请求失败" + e.getMessage());
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
	}
	
	//查询企业付款结果
	public static String gettransferinfo (String requestXml,String entrance,String mchid){
		String url="";
		if (entrance.equals("") || entrance.equals("1")) {
			url="1/apiclient_cert.p12";
		}else{
			url=entrance+"/apiclient_cert.p12";
		}
		try {
			KeyStore keyStore  = KeyStore.getInstance("PKCS12");
		    FileInputStream instream = new FileInputStream(new File(url));//P12文件目录
			try {
	            keyStore.load(instream, mchid.toCharArray());//这里写密码..默认是你的MCHID
	        } finally {
	            instream.close();
	        }
	      
	        // Trust own CA and all self-signed certs
	        SSLContext sslcontext = SSLContexts.custom()
	                .loadKeyMaterial(keyStore, mchid.toCharArray())//这里也是写密码的,..默认是你的MCHID
	                .build();
	        // Allow TLSv1 protocol only
	        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
	                sslcontext,
	                new String[] { "TLSv1" },
	                null,
	                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
	        CloseableHttpClient httpclient = HttpClients.custom()
	                .setSSLSocketFactory(sslsf)
	                .build();
			final String OPENAPI_ADDRESS = "https://api.mch.weixin.qq.com/mmpaymkttransfers/gettransferinfo";
			final String CHARSET = "UTF-8";
			HttpPost post = new HttpPost(OPENAPI_ADDRESS);
			//String proxyUrl = "10.26.98.138";
			//int proxyPoint = 3128;
			RequestConfig defaultRequestConfig = RequestConfig.custom().
					setSocketTimeout(5000).setConnectTimeout(5000).setMaxRedirects(5000).
					setConnectionRequestTimeout(5000).setStaleConnectionCheckEnabled(true).build();
			RequestConfig requestConfig = RequestConfig.copy(defaultRequestConfig)
					.setProxy(new HttpHost(proxy_ip, Integer.valueOf(proxy_port)))
					.build();
			post.setConfig(requestConfig);
			post.setEntity(new StringEntity(requestXml, ContentType.create(
					"application/x-www-form-urlencoded", CHARSET)));
			HttpResponse response = httpclient.execute(post);
			HttpEntity entity = response.getEntity();
			String body = EntityUtils.toString(entity, CHARSET);
			return body;
		} catch (Exception e) {
			throw new IllegalStateException("通讯请求失败" + e.getMessage());
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
	}


}
