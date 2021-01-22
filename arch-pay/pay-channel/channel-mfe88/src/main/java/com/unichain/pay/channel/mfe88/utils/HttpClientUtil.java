package com.unichain.pay.channel.mfe88.utils;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpClientUtil {
    // private static Logger log = Logger.getRootLogger();

    public static HttpResponse sendRequest(CloseableHttpClient httpclient, HttpPost httpPost) {
        CloseableHttpResponse response = null;
        try {
            httpclient.execute(httpPost);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static String parseReponse(HttpResponse response) {
        HttpEntity entity = response.getEntity();
        String body = null;
        try {
            String charset = EntityUtils.getContentCharSet(entity);
            body = EntityUtils.toString(entity);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body;
    }

    public static HttpPost postForm(String url, Map<String, Object> param) {
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> nvpr = new ArrayList<NameValuePair>();
        Set<String> keySet = param.keySet();
        for (String key : keySet) {
            BasicNameValuePair bnvp = new BasicNameValuePair(key, param.get(key) + "");
            nvpr.add(bnvp);
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvpr, HTTP.UTF_8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return httpPost;
    }

    public static String sslPost(String url, Map<String, Object> param) {
        // CloseableHttpClient httpclient =
        // SSLUtil.createSSLClientDefaultAllowAllHostname();
        CloseableHttpClient httpclient = null;
        HttpPost httpPost = postForm(url, param);
        String body = null;
        try {
            Builder configBuilder = RequestConfig.custom();
            configBuilder.setConnectTimeout(10 * 1000);
            configBuilder.setSocketTimeout(10 * 1000);
            HttpClientBuilder clientBuilder = HttpClients.custom();
            clientBuilder.setDefaultRequestConfig(configBuilder.build());
            httpclient = clientBuilder.build();
            HttpResponse response = httpclient.execute(httpPost);
            body = parseReponse(response);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body;
    }

    public static String post(String url, Map<String, Object> param) throws Exception {
        CloseableHttpClient httpclient = null;
        HttpPost httpPost = postForm(url, param);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(1000).setSocketTimeout(30000).build();
        httpPost.setConfig(requestConfig);
        String body = null;
        try {
            Builder configBuilder = RequestConfig.custom();
            configBuilder.setConnectTimeout(10 * 1000);
            configBuilder.setSocketTimeout(10 * 1000);
            HttpClientBuilder clientBuilder = HttpClients.custom();
            clientBuilder.setDefaultRequestConfig(configBuilder.build());
            httpclient = clientBuilder.build();
            HttpResponse response = httpclient.execute(httpPost);
            body = parseReponse(response);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body;
    }

    /**
     * post请求
     *
     * @param url  请求地址
     * @param data 请求参数
     * @return 返回body
     */
    public static String post(String url, String data) {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);

        String body = null;
        try {
            // 构�?�最�?单的字符串数�?
            StringEntity reqEntity = new StringEntity(data, Charset.forName("utf-8"));
            // 设置类型
            reqEntity.setContentType("application/x-www-form-urlencoded");
            reqEntity.setContentEncoding("UTF-8");
            // 设置请求的数�?
            httppost.setEntity(reqEntity);
            // 执行
            HttpResponse httpresponse = httpclient.execute(httppost);
            HttpEntity entity = httpresponse.getEntity();
            body = EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body;
    }

    public static void main(String[] args) throws Exception {
    }
}
