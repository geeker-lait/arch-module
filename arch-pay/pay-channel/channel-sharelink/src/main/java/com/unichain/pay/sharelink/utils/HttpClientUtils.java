package com.unichain.pay.sharelink.utils;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Function : TODO<br/>
 * Date : 2016年1月5日 下午3:34:06 <br/>
 *
 * @author pengxiao
 * @since JDK 1.7
 */
public class HttpClientUtils {

    /**
     * 连接超时时间
     */
    public static final int CONNECTION_TIMEOUT_MS = 60000;
    /**
     * 读取数据超时时间
     */
    public static final int SO_TIMEOUT_MS = 60000;
    public static final String CONTENT_TYPE_JSON_CHARSET = "application/json;charset=gbk";
    public static final String CONTENT_TYPE_XML_CHARSET = "application/xml;charset=gbk";
    /**
     * httpclient读取内容时使用的字符集
     */
    public static final String CONTENT_CHARSET = "GBK";
    public static final Charset UTF_8 = Charset.forName("UTF-8");
    public static final Charset GBK = Charset.forName(CONTENT_CHARSET);
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);


    private HttpClientUtils() {
    }

    /**
     * 简单get调用
     *
     * @param url
     * @param params
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @throws URISyntaxException
     */
    public static String httpGetInvoke(String url, Map<String, String> params)
            throws ClientProtocolException, IOException, URISyntaxException {
        return getMethordInvoke(url, params, CONTENT_CHARSET);
    }

    /**
     * 返回文件流
     *
     * @param url
     * @param params
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @throws URISyntaxException
     */
    public static InputStream httpGetInputStream(String url, Map<String, String> params)
            throws ClientProtocolException, IOException, URISyntaxException {
        return getMethordInputStream(url, params, CONTENT_CHARSET);
    }

    /**
     * 简单get调用
     *
     * @param url
     * @param params
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @throws URISyntaxException
     */
    public static String getMethordInvoke(String url,
                                          Map<String, String> params, String charset)
            throws ClientProtocolException, IOException, URISyntaxException {

        HttpClient client = buildHttpClient(false);

        HttpGet get = buildHttpGet(url, params);

        HttpResponse response = client.execute(get);

        assertStatus(response);

        HttpEntity entity = response.getEntity();
        if (entity != null) {
            String returnStr = EntityUtils.toString(entity, charset);
            return returnStr;
        }
        return null;
    }

    /**
     * 返回一个文件流
     *
     * @param url
     * @param params
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @throws URISyntaxException
     */
    public static InputStream getMethordInputStream(String url,
                                                    Map<String, String> params, String charset)
            throws ClientProtocolException, IOException, URISyntaxException {

        HttpClient client = buildHttpClient(false);

        HttpGet get = buildHttpGet(url, params);

        HttpResponse response = client.execute(get);

        assertStatus(response);

        HttpEntity entity = response.getEntity();
        if (entity != null) {
            //application/octet-stream 或者 application/zip
            org.apache.http.Header d = entity.getContentType();
            logger.info("响应类型为:{},响应名是:{}", d.getValue(), d.getName());
            if (!d.getValue().equals("APPLICATION/OCTET-STREAM")) {
                BufferedReader in = new BufferedReader(new InputStreamReader(entity.getContent()));
                String s;
                try {
                    while ((s = in.readLine()) != null)
                        logger.info("解析字节流输出:{}", s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
            return entity.getContent();
        }
        return null;
    }

    /**
     * 简单post调用
     *
     * @param url
     * @param params
     * @return
     * @throws URISyntaxException
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String httpPostInvoke(String url, Map<String, String> params)
            throws URISyntaxException, ClientProtocolException, IOException {
        return postMethordInvoke(url, params, CONTENT_CHARSET);
    }

    /**
     * 简单post调用
     *
     * @param url
     * @param params
     * @return
     * @throws URISyntaxException
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String postMethordInvoke(String url,
                                           Map<String, String> params, String charset)
            throws URISyntaxException, ClientProtocolException, IOException {

        HttpClient client = buildHttpClient(false);

        HttpPost postMethod = buildHttpPost(url, params);

        HttpResponse response = client.execute(postMethod);

        assertStatus(response);

        HttpEntity entity = response.getEntity();

        if (entity != null) {
            String returnStr = EntityUtils.toString(entity, charset);
            return returnStr;
        }

        return null;
    }

    /**
     * 简单post调用(正向代理)
     *
     * @param url
     * @param params
     * @return
     * @throws URISyntaxException
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String postMethordInvoke(String url,
                                           Map<String, String> params, String charset, boolean useProxy)
            throws URISyntaxException, ClientProtocolException, IOException {
        InetSocketAddress inetSocketAddress = null;
        if (useProxy) {
            List<Proxy> proxyList = ProxySelector.getDefault().select(new URI(url));
            if (proxyList.size() > 0 && !Proxy.NO_PROXY.equals(proxyList.get(0))) {
                inetSocketAddress = (InetSocketAddress) proxyList.get(0).address();
            }
        }
        HttpClient client = buildHttpClientForProxy(false);
        HttpPost postMethod = buildHttpPostForProxy(url, params, inetSocketAddress);

        HttpResponse response = client.execute(postMethod);

        assertStatus(response);

        HttpEntity entity = response.getEntity();

        if (entity != null) {
            String returnStr = EntityUtils.toString(entity, charset);
            return returnStr;
        }

        return null;
    }

    /**
     * 简单post调用
     *
     * @param url
     * @param params
     * @return
     * @throws URISyntaxException
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String postMethordInvoke(String url, Map<String, String> headerMap, HttpEntity requestEntity, String charset)
            throws URISyntaxException, ClientProtocolException, IOException {
        HttpClient client = buildHttpClient(false);
        HttpPost post = new HttpPost(url);
        post.setConfig(HttpClientUtils.buildRequestConfig());

        if (headerMap != null) {
            for (String key : headerMap.keySet()) {
                post.setHeader(key, headerMap.get(key));
            }
        }
        post.setEntity(requestEntity);

        HttpResponse response = client.execute(post);
        assertStatus(response);
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            String returnStr = EntityUtils.toString(entity, charset);
            return returnStr;
        }

        return null;
    }

    /**
     * 简单post调用
     *
     * @param url
     * @param params
     * @return
     * @throws URISyntaxException
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String postMethordInvoke(String url, Map<String, String> headerMap, RequestConfig requestConfig, HttpEntity requestEntity, String charset)
            throws URISyntaxException, ClientProtocolException, IOException {
        HttpClient client = buildHttpClient(false);
        HttpPost post = new HttpPost(url);
        post.setConfig(requestConfig);

        if (headerMap != null) {
            for (String key : headerMap.keySet()) {
                post.setHeader(key, headerMap.get(key));
            }
        }
        post.setEntity(requestEntity);

        HttpResponse response = client.execute(post);
        assertStatus(response);
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            String returnStr = EntityUtils.toString(entity, charset);
            return returnStr;
        }

        return null;
    }

    /**
     * 创建HttpClient
     *
     * @param isMultiThread
     * @return
     */
    public static HttpClient buildHttpClient(boolean isMultiThread) {

        CloseableHttpClient client;

        if (isMultiThread)
            client = HttpClientBuilder
                    .create()
                    .setConnectionManager(
                            new PoolingHttpClientConnectionManager()).build();
        else
            client = HttpClientBuilder.create().build();
        // 设置代理服务器地址和端口
        // client.getHostConfiguration().setProxy("proxy_host_addr",proxy_port);
        return client;
    }

    /**
     * 创建HttpClient
     *
     * @param isMultiThread
     * @return
     */
    public static HttpClient buildHttpClientForProxy(boolean isMultiThread) {

        CloseableHttpClient client;

        if (isMultiThread)
            client = HttpClientBuilder
                    .create()
                    .setConnectionManager(
                            new PoolingHttpClientConnectionManager()).build();
        else
            client = HttpClientBuilder.create().build();
        // 设置代理服务器地址和端口
        // client.getHostConfiguration().setProxy("proxy_host_addr",proxy_port);
        return client;
    }

    /**
     * 构建httpPost对象
     *
     * @param url
     * @param headers
     * @return
     * @throws UnsupportedEncodingException
     * @throws URISyntaxException
     */
    public static HttpPost buildHttpPost(String url, Map<String, String> params)
            throws UnsupportedEncodingException, URISyntaxException {
        Assert.notNull(url, "构建HttpPost时,url不能为null");
        HttpPost post = new HttpPost(url);
        post.setConfig(buildRequestConfig());//设置请求和传输超时时间

        setCommonHttpMethod(post);
        HttpEntity he = null;
        if (params != null) {
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            for (String key : params.keySet()) {
                formparams.add(new BasicNameValuePair(key, params.get(key)));
            }
            he = new UrlEncodedFormEntity(formparams, GBK);
            try {
                logger.info(EntityUtils.toString(he));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            post.setEntity(he);
        }
        // 在RequestContent.process中会自动写入消息体的长度，自己不用写入，写入反而检测报错
        // setContentLength(post, he);
        return post;

    }

    /**
     * 构建httpPost对象
     *
     * @param url
     * @param headers
     * @return
     * @throws UnsupportedEncodingException
     * @throws URISyntaxException
     */
    public static HttpPost buildHttpPostForProxy(String url, Map<String, String> params, InetSocketAddress inetSocketAddress)
            throws UnsupportedEncodingException, URISyntaxException {
        Assert.notNull(url, "构建HttpPost时,url不能为null");
        HttpPost post = new HttpPost(url);
        post.setConfig(buildRequestConfig());//设置请求和传输超时时间

        if (inetSocketAddress != null) {
            HttpHost proxy = new HttpHost(inetSocketAddress.getHostName(), inetSocketAddress.getPort(), "http");
            RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
            post.setConfig(config);
        }

        setCommonHttpMethod(post);
        HttpEntity he = null;
        if (params != null) {
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            for (String key : params.keySet()) {
                formparams.add(new BasicNameValuePair(key, params.get(key)));
            }
            he = new UrlEncodedFormEntity(formparams, GBK);
            try {
                logger.info(EntityUtils.toString(he));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            post.setEntity(he);
        }
        // 在RequestContent.process中会自动写入消息体的长度，自己不用写入，写入反而检测报错
        // setContentLength(post, he);
        return post;

    }

    /**
     * 构建httpGet对象
     *
     * @param url
     * @param headers
     * @return
     * @throws URISyntaxException
     */
    public static HttpGet buildHttpGet(String url, Map<String, String> params)
            throws URISyntaxException {
        Assert.notNull(url, "构建HttpGet时,url不能为null");
        HttpGet get = new HttpGet(buildGetUrl(url, params));
        get.setConfig(buildRequestConfig());//设置请求和传输超时时间
        return get;
    }

    /**
     * build getUrl str
     *
     * @param url
     * @param params
     * @return
     */
    private static String buildGetUrl(String url, Map<String, String> params) {
        StringBuffer uriStr = new StringBuffer(url);
        if (params != null) {
            List<NameValuePair> ps = new ArrayList<NameValuePair>();
            for (String key : params.keySet()) {
                ps.add(new BasicNameValuePair(key, params.get(key)));
            }
            uriStr.append("?");
            uriStr.append(URLEncodedUtils.format(ps, UTF_8));
        }
        return uriStr.toString();
    }

    /**
     * 设置HttpMethod通用配置
     *
     * @param httpMethod
     */
    public static void setCommonHttpMethod(HttpRequestBase httpMethod) {
        httpMethod.setHeader(HTTP.CONTENT_ENCODING, CONTENT_CHARSET);
        // setting
        // contextCoding
        // httpMethod.setHeader(HTTP.CHARSET_PARAM,
        // CONTENT_CHARSET);
        // httpMethod.setHeader(HTTP.CONTENT_TYPE, CONTENT_TYPE_JSON_CHARSET);
        // httpMethod.setHeader(HTTP.CONTENT_TYPE, CONTENT_TYPE_XML_CHARSET);
    }

    /**
     * 设置成消息体的长度 setting MessageBody length
     *
     * @param httpMethod
     * @param he
     */
    public static void setContentLength(HttpRequestBase httpMethod,
                                        HttpEntity he) {
        if (he == null) {
            return;
        }
        httpMethod.setHeader(HTTP.CONTENT_LEN,
                String.valueOf(he.getContentLength()));
    }

    /**
     * 构建公用RequestConfig
     *
     * @return
     */
    public static RequestConfig buildRequestConfig() {
        // 设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(SO_TIMEOUT_MS)
                .setConnectTimeout(CONNECTION_TIMEOUT_MS).build();
        return requestConfig;
    }

    /**
     * 强验证必须是200状态否则报异常
     *
     * @param res
     * @throws HttpException
     */
    static void assertStatus(HttpResponse res) throws IOException {
        Assert.notNull(res, "http响应对象为null");
        Assert.notNull(res.getStatusLine(), "http响应对象的状态为null");
        logger.info("http响应对象的状态为：{}", res.getStatusLine().getStatusCode());
        switch (res.getStatusLine().getStatusCode()) {
            case HttpStatus.SC_OK:
//			 case HttpStatus.SC_CREATED:
//			 case HttpStatus.SC_ACCEPTED:
//			 case HttpStatus.SC_NON_AUTHORITATIVE_INFORMATION:
//			 case HttpStatus.SC_NO_CONTENT:
//			 case HttpStatus.SC_RESET_CONTENT:
//			 case HttpStatus.SC_PARTIAL_CONTENT:
//			 case HttpStatus.SC_MULTI_STATUS:
                break;
            default:
                throw new IOException("服务器响应状态异常,失败.");
        }
    }
}
