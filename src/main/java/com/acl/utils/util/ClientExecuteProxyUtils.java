package com.acl.utils.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLContext;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.MessageConstraints;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.HttpConnectionFactory;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultHttpResponseParser;
import org.apache.http.impl.conn.DefaultHttpResponseParserFactory;
import org.apache.http.impl.conn.ManagedHttpClientConnectionFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;
import org.apache.http.impl.io.DefaultHttpRequestWriterFactory;
import org.apache.http.io.HttpMessageParser;
import org.apache.http.io.HttpMessageParserFactory;
import org.apache.http.io.HttpMessageWriterFactory;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicLineParser;
import org.apache.http.message.LineParser;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;

import com.acl.component.SystemConfig;
import com.acl.pojo.TxOrder;
import com.alibaba.fastjson.JSONObject;


/**
 * className:ClientExecuteProxyUtils 
 * author:malx 
 * createDate:2016年2月29日
 * 下午12:03:17 vsersion:1.0 
 * department:安创乐科技 
 * description:关于HttpClient的统一调用
 */
public class ClientExecuteProxyUtils {

	/**
	 * 发送手机验证码
	 * @throws IOException
	 * @throws ClientProtocolException
	 * @throws URISyntaxException 
	 */
	public static void sendPhoneCode(String mobile,String msg,boolean isok,String product, String extno) throws URISyntaxException {
		
		HttpMessageParserFactory<HttpResponse> responseParserFactory = new DefaultHttpResponseParserFactory() {
	      public HttpMessageParser<HttpResponse> create(SessionInputBuffer buffer, MessageConstraints constraints) {
	                LineParser lineParser = new BasicLineParser() {
	                    public Header parseHeader(final CharArrayBuffer buffer) {
	                        try {
	                            return super.parseHeader(buffer);
	                        } catch (ParseException ex) {
	                            return new BasicHeader(buffer.toString(), null);
	                        }
	                    }
	                };
	                
	                
	                return new DefaultHttpResponseParser(buffer, lineParser, DefaultHttpResponseFactory.INSTANCE, constraints) {
	                    protected boolean reject(final CharArrayBuffer line, int count) {
	                        return false;
	                    }

	                };
	            }

	        }; 
		HttpMessageWriterFactory<HttpRequest> requestWriterFactory = new DefaultHttpRequestWriterFactory();
		HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connFactory = new ManagedHttpClientConnectionFactory(
                requestWriterFactory, responseParserFactory);
		SSLContext sslcontext = SSLContexts.createSystemDefault();
	    Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
	            .register("http", PlainConnectionSocketFactory.INSTANCE)
	            .register("https", new SSLConnectionSocketFactory(sslcontext))
	            .build();
	    DnsResolver dnsResolver = new SystemDefaultDnsResolver() {
            public InetAddress[] resolve(final String host) throws UnknownHostException {
                if (host.equalsIgnoreCase("myhost")) {
                    return new InetAddress[] { InetAddress.getByAddress(new byte[] {127, 0, 0, 1}) };
                } else {
                    return super.resolve(host);
                }
            }
        };
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(
                socketFactoryRegistry, connFactory, dnsResolver);
        //设置连接池
        connManager.setMaxTotal(1500);
       
        connManager.setDefaultMaxPerRoute(400);
        
        CloseableHttpClient httpclient = HttpClients.custom()
                .setConnectionManager(connManager)
                .setProxy(new HttpHost("10.45.145.22", 3128))//设置代理,如果没有关闭 则关闭代理
                .build();
        
        HttpGet httpget = new HttpGet();
        GetMethod method = new GetMethod();
    	method.setQueryString(new NameValuePair[] {
				new NameValuePair("account", "Hbzyfr_mall"),
				new NameValuePair("pswd", "Hbzyfr888"),
				new NameValuePair("mobile", mobile),
				new NameValuePair("needstatus", String.valueOf(isok)),
				new NameValuePair("msg", msg),
				new NameValuePair("product", product),
				new NameValuePair("extno", extno),
			});
    	
    	String url="http://222.73.117.156/msg/HttpBatchSendSM?"+method.getQueryString();
    	URI base=new URI(url);
        
        httpget.setURI(base);
        CloseableHttpResponse response;
		try {
			response = httpclient.execute(httpget);
			System.out.println(EntityUtils.toString(response.getEntity()));
			System.out.println("手机号码的回调函数如果有此函数说明正确!");
			response.close(); 
			httpclient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取有连接池的的httpClient
	 * @return
	 */
	public static CloseableHttpClient getHttpClient(){
		 HttpMessageParserFactory<HttpResponse> responseParserFactory = new DefaultHttpResponseParserFactory() {
	            public HttpMessageParser<HttpResponse> create(
	                SessionInputBuffer buffer, MessageConstraints constraints) {
	                LineParser lineParser = new BasicLineParser() {
	                    public Header parseHeader(final CharArrayBuffer buffer) {
	                        try {
	                            return super.parseHeader(buffer);
	                        } catch (ParseException ex) {
	                            return new BasicHeader(buffer.toString(), null);
	                        }
	                    }
	                };
	                return new DefaultHttpResponseParser(
	                    buffer, lineParser, DefaultHttpResponseFactory.INSTANCE, constraints) {
	                    protected boolean reject(final CharArrayBuffer line, int count) {
	                        return false;
	                    }

	                };
	            }

	        }; 
		HttpMessageWriterFactory<HttpRequest> requestWriterFactory = new DefaultHttpRequestWriterFactory();
		HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connFactory = new ManagedHttpClientConnectionFactory(
             requestWriterFactory, responseParserFactory);
		SSLContext sslcontext = SSLContexts.createSystemDefault();
	    Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
	            .register("http", PlainConnectionSocketFactory.INSTANCE)
	            .register("https", new SSLConnectionSocketFactory(sslcontext))
	            .build();
	    DnsResolver dnsResolver = new SystemDefaultDnsResolver() {
         public InetAddress[] resolve(final String host) throws UnknownHostException {
             if (host.equalsIgnoreCase("myhost")) {
                 return new InetAddress[] { InetAddress.getByAddress(new byte[] {127, 0, 0, 1}) };
             } else {
                 return super.resolve(host);
             }
         }
     };
     PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(
             socketFactoryRegistry, connFactory, dnsResolver);
     //设置连接池
     connManager.setMaxTotal(1500);
    
     connManager.setDefaultMaxPerRoute(400);
     
     CloseableHttpClient httpclient = HttpClients.custom()
             .setConnectionManager(connManager)
             .setProxy(new HttpHost("10.45.145.22", 3128))//设置代理,如果没有关闭 则关闭代理
             .build();
      return httpclient;
	}
	
	
	
	/** 
	 * 摩宝提现审核
	 */ 
	public static void sendTxCode(String orderId,String comefrom )throws URISyntaxException {
		 HttpMessageParserFactory<HttpResponse> responseParserFactory = new DefaultHttpResponseParserFactory() {
	            public HttpMessageParser<HttpResponse> create(
	                SessionInputBuffer buffer, MessageConstraints constraints) {
	                LineParser lineParser = new BasicLineParser() {
	                    public Header parseHeader(final CharArrayBuffer buffer) {
	                        try {
	                            return super.parseHeader(buffer);
	                        } catch (ParseException ex) {
	                            return new BasicHeader(buffer.toString(), null);
	                        }
	                    }
	                };
	                return new DefaultHttpResponseParser(
	                    buffer, lineParser, DefaultHttpResponseFactory.INSTANCE, constraints) {
	                    protected boolean reject(final CharArrayBuffer line, int count) {
	                        return false;
	                    }

	                };
	            }

	        }; 
		HttpMessageWriterFactory<HttpRequest> requestWriterFactory = new DefaultHttpRequestWriterFactory();
		HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connFactory = new ManagedHttpClientConnectionFactory(
               requestWriterFactory, responseParserFactory);
		SSLContext sslcontext = SSLContexts.createSystemDefault();
	    Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
	            .register("http", PlainConnectionSocketFactory.INSTANCE)
	            .register("https", new SSLConnectionSocketFactory(sslcontext))
	            .build();
	    DnsResolver dnsResolver = new SystemDefaultDnsResolver() {
           public InetAddress[] resolve(final String host) throws UnknownHostException {
               if (host.equalsIgnoreCase("myhost")) {
                   return new InetAddress[] { InetAddress.getByAddress(new byte[] {127, 0, 0, 1}) };
               } else {
                   return super.resolve(host);
               }
           }
       };
       PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(
               socketFactoryRegistry, connFactory, dnsResolver);
       //设置连接池
       connManager.setMaxTotal(1500);
      
       connManager.setDefaultMaxPerRoute(400);
       
       CloseableHttpClient httpclient = HttpClients.custom()
               .setConnectionManager(connManager)
//               .setProxy(new HttpHost("10.45.145.22", 3128))//设置代理,如果没有关闭 则关闭代理
               .build();
       
       HttpGet httpget = new HttpGet();
       GetMethod method = new GetMethod();
   	method.setQueryString(new NameValuePair[] {
				new NameValuePair("orderId", orderId),
			//	new NameValuePair("database", database),
			});
   	
   	String url = "";
   	System.out.println("将要发送摩宝http请求");
   	if(comefrom.equals("wechat")){  //如果来自微信
   		url="http://zf.caiquu.com/mobaopay1/mobao/submitWithdraw?"+method.getQueryString();
   	}else {
   		url="http://pay.xinchudz.com:9080/mobaopay/mobao/submitWithdraw?"+method.getQueryString();
   	}
   	URI base=new URI(url);
       
       httpget.setURI(base);
       CloseableHttpResponse response;
		try {
			response = httpclient.execute(httpget);
			System.out.println(EntityUtils.toString(response.getEntity()));
			System.out.println("提现回调函数如果有此函数说明正确!");
			response.close(); 
			httpclient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	

	/** 
	 * 微信提现审核
	 */ 
	public static void wxTxsh(String orderno,String type)throws URISyntaxException {
		 HttpMessageParserFactory<HttpResponse> responseParserFactory = new DefaultHttpResponseParserFactory() {
	            public HttpMessageParser<HttpResponse> create(
	                SessionInputBuffer buffer, MessageConstraints constraints) {
	                LineParser lineParser = new BasicLineParser() {
	                    public Header parseHeader(final CharArrayBuffer buffer) {
	                        try {
	                            return super.parseHeader(buffer);
	                        } catch (ParseException ex) {
	                            return new BasicHeader(buffer.toString(), null);
	                        }
	                    }
	                };
	                return new DefaultHttpResponseParser(
	                    buffer, lineParser, DefaultHttpResponseFactory.INSTANCE, constraints) {
	                    protected boolean reject(final CharArrayBuffer line, int count) {
	                        return false;
	                    }

	                };
	            }

	        }; 
		HttpMessageWriterFactory<HttpRequest> requestWriterFactory = new DefaultHttpRequestWriterFactory();
		HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connFactory = new ManagedHttpClientConnectionFactory(
               requestWriterFactory, responseParserFactory);
		SSLContext sslcontext = SSLContexts.createSystemDefault();
	    Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
	            .register("http", PlainConnectionSocketFactory.INSTANCE)
	            .register("https", new SSLConnectionSocketFactory(sslcontext))
	            .build();
	    DnsResolver dnsResolver = new SystemDefaultDnsResolver() {
           public InetAddress[] resolve(final String host) throws UnknownHostException {
               if (host.equalsIgnoreCase("myhost")) {
                   return new InetAddress[] { InetAddress.getByAddress(new byte[] {127, 0, 0, 1}) };
               } else {
                   return super.resolve(host);
               }
           }
       };
       PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(
               socketFactoryRegistry, connFactory, dnsResolver);
       //设置连接池
       connManager.setMaxTotal(1500);
      
       connManager.setDefaultMaxPerRoute(400);
       
       CloseableHttpClient httpclient = HttpClients.custom()
               .setConnectionManager(connManager)
//               .setProxy(new HttpHost("10.45.145.22", 3128))//设置代理,如果没有关闭 则关闭代理
               .build();
   
   	
   	/*****************post请求******************/
    HttpPost httppost = new HttpPost();
    PostMethod postMethod = new PostMethod();
    postMethod.setQueryString(new NameValuePair[] {
				new NameValuePair("orderno", orderno),
				new NameValuePair("type", type),
				//new NameValuePair("database", database),
			});
    
   	String url="http://127.0.0.1:8216/withdrawals/weiZhiFuCorpTransfer?"+postMethod.getQueryString();
   	URI base=new URI(url);
   	httppost.setURI(base);
       CloseableHttpResponse response;
		try {
			response = httpclient.execute(httppost);
			System.out.println(EntityUtils.toString(response.getEntity()));
			System.out.println("提现回调函数如果有此函数说明正确!");
			response.close(); 
			httpclient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
  	/*****************post请求******************/  
	}
	
	
	
	/** 
	 * 财趣提现审核
	 */ 
	public static void cqTxsh(String orderno,String type)throws URISyntaxException {
			System.out.println("财趣提现审核http请求准备发送");
		 HttpMessageParserFactory<HttpResponse> responseParserFactory = new DefaultHttpResponseParserFactory() {
	            public HttpMessageParser<HttpResponse> create(
	                SessionInputBuffer buffer, MessageConstraints constraints) {
	                LineParser lineParser = new BasicLineParser() {
	                    public Header parseHeader(final CharArrayBuffer buffer) {
	                        try {
	                            return super.parseHeader(buffer);
	                        } catch (ParseException ex) {
	                            return new BasicHeader(buffer.toString(), null);
	                        }
	                    }
	                };
	                return new DefaultHttpResponseParser(
	                    buffer, lineParser, DefaultHttpResponseFactory.INSTANCE, constraints) {
	                    protected boolean reject(final CharArrayBuffer line, int count) {
	                        return false;
	                    }

	                };
	            }

	        }; 
		HttpMessageWriterFactory<HttpRequest> requestWriterFactory = new DefaultHttpRequestWriterFactory();
		HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connFactory = new ManagedHttpClientConnectionFactory(
               requestWriterFactory, responseParserFactory);
		SSLContext sslcontext = SSLContexts.createSystemDefault();
	    Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
	            .register("http", PlainConnectionSocketFactory.INSTANCE)
	            .register("https", new SSLConnectionSocketFactory(sslcontext))
	            .build();
	    DnsResolver dnsResolver = new SystemDefaultDnsResolver() {
           public InetAddress[] resolve(final String host) throws UnknownHostException {
               if (host.equalsIgnoreCase("myhost")) {
                   return new InetAddress[] { InetAddress.getByAddress(new byte[] {127, 0, 0, 1}) };
               } else {
                   return super.resolve(host);
               }
           }
       };
       PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(
               socketFactoryRegistry, connFactory, dnsResolver);
       //设置连接池
       connManager.setMaxTotal(1500);
      
       connManager.setDefaultMaxPerRoute(400);
       
       CloseableHttpClient httpclient = HttpClients.custom()
               .setConnectionManager(connManager)
//               .setProxy(new HttpHost("10.45.145.22", 3128))//设置代理,如果没有关闭 则关闭代理
               .build();
		
   	/*****************post请求******************/
    HttpPost httppost = new HttpPost();
    PostMethod postMethod = new PostMethod();
    postMethod.setQueryString(new NameValuePair[] {
				new NameValuePair("orderno", orderno),
				new NameValuePair("type", type),
				//new NameValuePair("database", database),
			});
    
   	String url="http://127.0.0.1:8219/withdrawals/wechatCorpTransfer?"+postMethod.getQueryString();
//  	String url="http://malixi.xicp.cn/withdrawals/wechatCorpTransfer?"+postMethod.getQueryString();
   	URI base=new URI(url);
   	httppost.setURI(base);
       CloseableHttpResponse response;
		try {
			response = httpclient.execute(httppost);
			System.out.println(EntityUtils.toString(response.getEntity()));
			System.out.println("财趣提现回调函数如果有此函数说明正确!");
			response.close(); 
			httpclient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
  	/*****************post请求******************/
		
		
	}

	/** 
	 * 财趣提现审核
	 */ 
	public static void testHttps(String orderno,String type)throws URISyntaxException {
			System.out.println("财趣提现审核http请求准备发送");
		 HttpMessageParserFactory<HttpResponse> responseParserFactory = new DefaultHttpResponseParserFactory() {
	            public HttpMessageParser<HttpResponse> create(
	                SessionInputBuffer buffer, MessageConstraints constraints) {
	                LineParser lineParser = new BasicLineParser() {
	                    public Header parseHeader(final CharArrayBuffer buffer) {
	                        try {
	                            return super.parseHeader(buffer);
	                        } catch (ParseException ex) {
	                            return new BasicHeader(buffer.toString(), null);
	                        }
	                    }
	                };
	                return new DefaultHttpResponseParser(
	                    buffer, lineParser, DefaultHttpResponseFactory.INSTANCE, constraints) {
	                    protected boolean reject(final CharArrayBuffer line, int count) {
	                        return false;
	                    }

	                };
	            }

	        }; 
		HttpMessageWriterFactory<HttpRequest> requestWriterFactory = new DefaultHttpRequestWriterFactory();
		HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connFactory = new ManagedHttpClientConnectionFactory(
               requestWriterFactory, responseParserFactory);
		SSLContext sslcontext = SSLContexts.createSystemDefault();
	    Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
	            .register("http", PlainConnectionSocketFactory.INSTANCE)
	            .register("https", new SSLConnectionSocketFactory(sslcontext))
	            .build();
	    DnsResolver dnsResolver = new SystemDefaultDnsResolver() {
           public InetAddress[] resolve(final String host) throws UnknownHostException {
               if (host.equalsIgnoreCase("myhost")) {
                   return new InetAddress[] { InetAddress.getByAddress(new byte[] {127, 0, 0, 1}) };
               } else {
                   return super.resolve(host);
               }
           }
       };
       PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(
               socketFactoryRegistry, connFactory, dnsResolver);
       //设置连接池
       connManager.setMaxTotal(1500);
      
       connManager.setDefaultMaxPerRoute(400);
       
       CloseableHttpClient httpclient = HttpClients.custom()
               .setConnectionManager(connManager)
//               .setProxy(new HttpHost("10.45.145.22", 3128))//设置代理,如果没有关闭 则关闭代理
               .build();
		
   	/*****************post请求******************/
    HttpPost httppost = new HttpPost();
    PostMethod postMethod = new PostMethod();
    postMethod.setQueryString(new NameValuePair[] {
				new NameValuePair("orderno", orderno),
				new NameValuePair("type", type),
				//new NameValuePair("database", database),
			});
    
   	String url="https://admin.caiquu.com/?"+postMethod.getQueryString();
//  	String url="http://malixi.xicp.cn/withdrawals/wechatCorpTransfer?"+postMethod.getQueryString();
   	URI base=new URI(url);
   	httppost.setURI(base);
       CloseableHttpResponse response;
		try {
			response = httpclient.execute(httppost);
			System.out.println(EntityUtils.toString(response.getEntity()));
			System.out.println("财趣提现回调函数如果有此函数说明正确!");
			response.close(); 
			httpclient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
  	/*****************post请求******************/
		
		
	}
	
	
	/** 
	 * 一麻袋提现审核
	 */ 
	public static JSONObject yemadaiTxsh(TxOrder txOrder)throws URISyntaxException {
		JSONObject jObject = new JSONObject();
		HttpMessageParserFactory<HttpResponse> responseParserFactory = new DefaultHttpResponseParserFactory() {
	            public HttpMessageParser<HttpResponse> create(
	                SessionInputBuffer buffer, MessageConstraints constraints) {
	                LineParser lineParser = new BasicLineParser() {
	                    public Header parseHeader(final CharArrayBuffer buffer) {
	                        try {
	                            return super.parseHeader(buffer);
	                        } catch (ParseException ex) {
	                            return new BasicHeader(buffer.toString(), null);
	                        }
	                    }
	                };
	                return new DefaultHttpResponseParser(
	                    buffer, lineParser, DefaultHttpResponseFactory.INSTANCE, constraints) {
	                    protected boolean reject(final CharArrayBuffer line, int count) {
	                        return false;
	                    }

	                };
	            }

	        }; 
		HttpMessageWriterFactory<HttpRequest> requestWriterFactory = new DefaultHttpRequestWriterFactory();
		HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connFactory = new ManagedHttpClientConnectionFactory(
               requestWriterFactory, responseParserFactory);
		SSLContext sslcontext = SSLContexts.createSystemDefault();
	    Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
	            .register("http", PlainConnectionSocketFactory.INSTANCE)
	            .register("https", new SSLConnectionSocketFactory(sslcontext))
	            .build();
	    DnsResolver dnsResolver = new SystemDefaultDnsResolver() {
           public InetAddress[] resolve(final String host) throws UnknownHostException {
               if (host.equalsIgnoreCase("myhost")) {
                   return new InetAddress[] { InetAddress.getByAddress(new byte[] {127, 0, 0, 1}) };
               } else {
                   return super.resolve(host);
               }
           }
       };
       PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(
               socketFactoryRegistry, connFactory, dnsResolver);
       //设置连接池
       connManager.setMaxTotal(1500);
      
       connManager.setDefaultMaxPerRoute(400);
       
       CloseableHttpClient httpclient = HttpClients.custom()
               .setConnectionManager(connManager)
//               .setProxy(new HttpHost("10.45.145.22", 3128))//设置代理,如果没有关闭 则关闭代理
               .build();
       
     //  HttpGet httpget = new HttpGet();
       HttpPost httpPost=new HttpPost();
       GetMethod method = new GetMethod();
   	method.setQueryString(new NameValuePair[] {
   			    new NameValuePair("orderId",StringUtils.convertString(txOrder.getTx_order_id())),
		/*		new NameValuePair("user_bank", StringUtils.convertString(txOrder.getUser_bank())),//银行卡号
				new NameValuePair("user_bank_address", StringUtils.convertString(txOrder.getUser_bank_address())),//银行名称
				new NameValuePair("user_name", StringUtils.convertString(txOrder.getUser_name())),//银行户名
				new NameValuePair("user_prov", StringUtils.convertString(txOrder.getUser_prov())),//省
				new NameValuePair("user_city", StringUtils.convertString(txOrder.getUser_city())),//市
				new NameValuePair("tx_money", StringUtils.convertString(txOrder.getTx_money())),//金额
*/			});
   	
 //  	String url = "http://oray-acl.xicp.net/mobaopay/yimadai/submitWithdraw?"+method.getQueryString();
 	String url = "http://mb.caiquu.com/mobaopay/yimadai/submitWithdraw?"+method.getQueryString();
   	
   	System.out.println("将要发送一麻袋http请求");
   	URI base=new URI(url);
       
   	httpPost.setURI(base);
       CloseableHttpResponse response;
		try {
			response = httpclient.execute(httpPost);
			
			String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");
			JSONObject jsonObject = JSONObject.parseObject(jsonStr);
//			String str=StringUtils.convertString(EntityUtils.toString(response.getEntity()));
			System.out.println(jsonObject);
			
			
			String success = jsonObject.getString("success");
			String message = jsonObject.getString("msg");
			
			System.out.println("提现回调函数如果有此函数说明正确!");
			if("true".equalsIgnoreCase(success)){
				jObject.put("success", 1);
				jObject.put("msg", message);
			}else {
				jObject.put("success", 0);
				jObject.put("msg", message);
			}
			response.close(); 
			httpclient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jObject;
	}

	
	//httpPost.setEntity(new UrlEncodedFormEntity(nvps, charsetEncoding));
	
	//HttpResponse httpResponse = httpClient.execute(httpPost);
	
	//payUrl,paymap,"GBK"
	/** 
	 * 银联提现
	 */ 
/*	public static String httpPost(String url,Map<String, String> paymap,String gbk)throws URISyntaxException {

		HttpMessageParserFactory<HttpResponse> responseParserFactory = new DefaultHttpResponseParserFactory() {
		      public HttpMessageParser<HttpResponse> create(SessionInputBuffer buffer, MessageConstraints constraints) {
		                LineParser lineParser = new BasicLineParser() {
		                    public Header parseHeader(final CharArrayBuffer buffer) {
		                        try {
		                            return super.parseHeader(buffer);
		                        } catch (ParseException ex) {
		                            return new BasicHeader(buffer.toString(), null);
		                        }
		                    }
		                };
		                
		                
		                return new DefaultHttpResponseParser(buffer, lineParser, DefaultHttpResponseFactory.INSTANCE, constraints) {
		                    protected boolean reject(final CharArrayBuffer line, int count) {
		                        return false;
		                    }

		                };
		            }

		        }; 
		HttpMessageWriterFactory<HttpRequest> requestWriterFactory = new DefaultHttpRequestWriterFactory();
		HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connFactory = new ManagedHttpClientConnectionFactory(
                requestWriterFactory, responseParserFactory);
		SSLContext sslcontext = SSLContexts.createSystemDefault();
	    Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
	            .register("http", PlainConnectionSocketFactory.INSTANCE)
	            .register("https", new SSLConnectionSocketFactory(sslcontext))
	            .build();
	    DnsResolver dnsResolver = new SystemDefaultDnsResolver() {
            public InetAddress[] resolve(final String host) throws UnknownHostException {
                if (host.equalsIgnoreCase("myhost")) {
                    return new InetAddress[] { InetAddress.getByAddress(new byte[] {127, 0, 0, 1}) };
                } else {
                    return super.resolve(host);
                }
            }
        };
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(
                socketFactoryRegistry, connFactory, dnsResolver);
        //设置连接池
        connManager.setMaxTotal(1500);
       
        connManager.setDefaultMaxPerRoute(400);
        
        CloseableHttpClient httpclient = HttpClients.custom()
                .setConnectionManager(connManager)
                .setProxy(new HttpHost("10.45.145.22", 3128))//设置代理,如果没有关闭 则关闭代理
                .build();
        
        HttpGet httpget = new HttpGet();
        GetMethod method = new GetMethod();
        
		paymap.put("merId", merId);
		paymap.put("merDate", merDate);
		paymap.put("merSeqId", merSeqId);
		paymap.put("cardNo", cardNo);
		paymap.put("usrName", usrName);
		paymap.put("openBank", openBank);
		paymap.put("prov", prov);
		paymap.put("city", city);
		paymap.put("transAmt", transAmt);
		paymap.put("purpose", purpose);
		paymap.put("subBank",subBank);
		paymap.put("flag", flag);
		paymap.put("version", version);
		paymap.put("chkValue",chkValue);
		paymap.put("termType",termType);
		paymap.put("signFlag", signFlag)
        
    	method.setQueryString(new NameValuePair[] {
				new NameValuePair("merId", paymap.get("merId")),
				new NameValuePair("merDate", paymap.get("merDate")),
				new NameValuePair("merSeqId", paymap.get("merSeqId")),
				new NameValuePair("cardNo", paymap.get("cardNo")),
				new NameValuePair("usrName", paymap.get("usrName")),
				new NameValuePair("openBank", paymap.get("openBank")),
				new NameValuePair("prov", paymap.get("prov")),
				new NameValuePair("city", paymap.get("city")),
				new NameValuePair("transAmt", paymap.get("transAmt")),
				new NameValuePair("purpose", paymap.get("purpose")),
				new NameValuePair("subBank", paymap.get("subBank")),
				new NameValuePair("flag", paymap.get("flag")),
				new NameValuePair("version", paymap.get("version")),
				new NameValuePair("chkValue", paymap.get("chkValue")),
				new NameValuePair("termType", paymap.get("termType")),
				new NameValuePair("signFlag", paymap.get("signFlag"))
				,
			});
    	
    	String uri=url+"?"+method.getQueryString();
    	URI base=new URI(uri);
        
        httpget.setURI(base);
        CloseableHttpResponse response;
		try {
			response = httpclient.execute(httpget);
			System.out.println(EntityUtils.toString(response.getEntity()));
			System.out.println("手机号码的回调函数如果有此函数说明正确!");
			response.close(); 
			httpclient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	*/
	
	public static void main(String []args) throws Exception{
//		testHttps("asd","asd","asd");
		System.out.println(com.alibaba.druid.filter.config.ConfigTools.encrypt("0B6DA6A3!Fi%-1(v)-2A4FC9D1CAB33B6"));
	}

}
