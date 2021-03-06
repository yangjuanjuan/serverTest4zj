package com.zjlab.qa.base;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class RestClient {

    private static HttpClientContext localContext = HttpClientContext.create();

    private static Header[] headers ;


    //本类中包含post get put delete请求方法
    //1 get请求 不带请求头
    /**
     *
     * @param url  请求地址
     * @return
     * @throws IOException
     */
    public static CloseableHttpResponse getApi(String url) throws IOException {
        //新建一个可关闭的HTTPclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //新建get对象
        HttpGet get = new HttpGet(url);
//        https://reqres.in/api/users?page=2
        //执行 get请求  存储返回的响应
        CloseableHttpResponse httpResponse = httpClient.execute(get,localContext);
        return httpResponse;
    }
    //2 get请求 带有请求头 方法重载
    public static CloseableHttpResponse getApi(String url , HashMap<String,String> headermap) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        //加载请求头到HTTP中
        for (Map.Entry<String, String> entry : headermap.entrySet()) {
            httpGet.addHeader(entry.getKey(),entry.getValue());
        }
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet,localContext);
        return httpResponse;
    }

    //2 get请求 带有请求头 带cookie
    public static CloseableHttpResponse getApi(String url , HashMap<String,String> headermap,Cookie cookie) throws IOException {
        CookieStore cookieStore = new BasicCookieStore();
        cookieStore.addCookie(cookie);
        localContext.setCookieStore(cookieStore);
        CloseableHttpResponse httpResponse = RestClient.getApi(url, headermap);
        return httpResponse;
    }
    /**
     *
     * @param url
     * @param entityString 设置json 请求参数
     * @param headermap 请求头
     * @return
     * @throws IOException
     */
    //3 post请求
    public static CloseableHttpResponse postApi(String url, String entityString, HashMap<String,String> headermap ) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        //设置payload
        httpPost.setEntity(new StringEntity(entityString,"utf-8"));
        //加载请求头对象到 httppost
        for (Map.Entry<String,String> entry : headermap.entrySet()){
            httpPost.addHeader(entry.getKey(),entry.getValue());
        }
        //发送post请求
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost,localContext);
        headers=httpResponse.getHeaders("Set-Cookie");
        return httpResponse;
    }
    //3 携带 cookie的post请求
    public static CloseableHttpResponse postApi(String url, String entityString, HashMap<String,String> headermap ,Cookie cookie) throws IOException {
        CookieStore cookieStore = new BasicCookieStore();
        cookieStore.addCookie(cookie);
        localContext.setCookieStore(cookieStore);
        //发送post请求
        CloseableHttpResponse httpResponse =RestClient.postApi(url,entityString,headermap);

        return httpResponse;
    }

    //3携带 cookie的上传文件
    public static CloseableHttpResponse uploadFile(String url,File file, Map<String, Object> params, Cookie cookie) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        CookieStore cookieStore = new BasicCookieStore();
        cookieStore.addCookie(cookie);
        localContext.setCookieStore(cookieStore);
        //HttpMultipartMode.RFC6532参数的设定是为避免文件名为中文时乱码
        MultipartEntityBuilder builder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.RFC6532);
        FileBody binFileBody = new FileBody(file);
        // add the file params
        builder.addPart("file",binFileBody);
        if (params != null && params.size() > 0) {
            Set<String> keys = params.keySet();
            for (String key : keys) {
                builder.addPart(key, new StringBody((String) params.get(key),ContentType.TEXT_PLAIN));
            }
        }
        HttpEntity reqEntity = builder.build();
        httpPost.setEntity(reqEntity);
        HashMap<String,String> header=new HashMap<String, String>();
        header.put("Content-Type","multipart/form-data");
        //发送post请求
        CloseableHttpResponse httpResponse =httpClient.execute(httpPost,localContext);
        return httpResponse;
    }

    //4 Put方法
    public static CloseableHttpResponse put(String url, String entityString, HashMap<String,String> headerMap) throws ClientProtocolException, IOException {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPut httpput = new HttpPut(url);
        httpput.setEntity(new StringEntity(entityString));
        for(Map.Entry<String, String> entry : headerMap.entrySet()) {
            httpput.addHeader(entry.getKey(), entry.getValue());
        }
        //发送put请求
        CloseableHttpResponse httpResponse = httpclient.execute(httpput,localContext);
        return httpResponse;
    }
    //5 Delete方法
    public static CloseableHttpResponse delete(String url) throws ClientProtocolException, IOException {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpDelete httpdel = new HttpDelete(url);
        //发送put请求
        CloseableHttpResponse httpResponse = httpclient.execute(httpdel,localContext);
        return httpResponse;
    }

    public static Header[] getHeaders() {
        return headers;
    }


    public static Cookie getCookieByName(String name){
        ArrayList<Cookie> cookies= (ArrayList<Cookie>) localContext.getCookieStore().getCookies();

        for (Cookie c:cookies) {
           if(c.getName().equals(name)) {
               return c;
           }
        }
        return null;
    }
}





