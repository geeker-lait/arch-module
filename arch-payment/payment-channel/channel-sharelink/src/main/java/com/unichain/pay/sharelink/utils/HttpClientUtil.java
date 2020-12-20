package com.unichain.pay.sharelink.utils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HttpClientUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    @Autowired
    private HttpClient httpClient;

    private static String getStringFromObject(Object obj) {
        if (obj == null) {
            return "";
        }
        return obj.toString();
    }

    public static String postToServerByXml(String xmlString, String url) throws UnsupportedEncodingException {

        HttpClient httpClient = new HttpClient();
        logger.info("调用servlet开始...");
        String retXmlString = "";
        PostMethod postMethod = new PostMethod(url);

        RequestEntity entity = new StringRequestEntity(xmlString, "text/xml", "utf8");
        postMethod.setRequestEntity(entity);
        //发送post请求
        try {
            logger.info("客户端请求报文：" + xmlString);
            httpClient.executeMethod(postMethod);
            int statusCode = postMethod.getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                retXmlString = postMethod.getResponseBodyAsString();
                logger.info("服务端返回报文：" + retXmlString);
            } else {
                retXmlString = statusCode + "";
            }
            postMethod.releaseConnection();
        } catch (HttpException e) {
            e.printStackTrace();
            postMethod.releaseConnection();
        } catch (IOException e) {
            e.printStackTrace();
            postMethod.releaseConnection();
        }
        logger.info("调用servlet结束...");
        return retXmlString;

    }

    public static void main(String[] args) {
        try {
            postToServerByXml("<hello><body>哈哈哈</body></hello>", "http://127.0.0.1:8080/pgw-pay/bindcard/bindcarddo");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String postToServer(Map params, String url) throws UnsupportedEncodingException {
        //HttpClient httpClient = new  HttpClient();
        logger.info("调用servlet开始...");
        String retrunVal = "";
        PostMethod postMethod = new PostMethod(url);
        NameValuePair[] nameValues = new NameValuePair[params.size()];
        Set paramsSet = params.entrySet();
        int i = 0;
        for (Iterator iterator = paramsSet.iterator(); iterator.hasNext(); ) {
            Map.Entry entry = (Map.Entry) iterator.next();
            nameValues[i] = new NameValuePair(getStringFromObject(entry.getKey()), getStringFromObject(entry.getValue()));
            i++;
        }
        //设置编码格式
        postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        postMethod.setRequestBody(nameValues);
        //	postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
        //	postMethod.setQueryString(nameValues);
        try {
            httpClient.executeMethod(postMethod);
            int statusCode = postMethod.getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                //服务器返回字符类型
                String responseCharset = postMethod.getResponseCharSet();
                System.out.println("responseCharset=" + responseCharset);
                retrunVal = new String(postMethod.getResponseBody(), StandardCharsets.UTF_8);
                System.out.println("retrunVal=" + retrunVal);
            } else {
                retrunVal = statusCode + "";
            }
            postMethod.releaseConnection();
        } catch (HttpException e) {
            e.printStackTrace();
            postMethod.releaseConnection();
        } catch (IOException e) {
            e.printStackTrace();
            postMethod.releaseConnection();
        }
        return retrunVal;
    }

}
