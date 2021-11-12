package com.zjlab.qa.base;

import com.alibaba.fastjson.JSONObject;
import com.zjlab.qa.common.ParseKeyword;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.cookie.Cookie;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class ApiBaseClient {

    protected HashMap<String,String> headermap;
    private ResourceBundle rb ;
    private String params ;
    private String url;


    public ApiBaseClient(){
        headermap=new HashMap<String,String>();
        headermap.put("Content-type","application/json");
        rb= ResourceBundle.getBundle("config/config", Locale.getDefault());
    }
    private String getValue(String key){
        return rb.getString(key);
    }

    public String setUrl(String path){
        url=getValue("HOST")+getValue(path);
        return url;
    }

    public CloseableHttpResponse run(String req,String apiName,Cookie cookie){
        this.setUrl(apiName);
        CloseableHttpResponse response = null;
        params= JSONObject.parseObject(req).toJSONString();
        try {
            response= RestClient.postApi(url,params,headermap,cookie);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  response;
    }
    public CloseableHttpResponse uploadFile(String fileName, Map<String,Object>others , String apiName, Cookie cookie){
        this.setUrl(apiName);
        CloseableHttpResponse response = null;
        File file = new File(fileName);
        try {
            response= RestClient.uploadFile(url,file,others,cookie);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  response;
    }

    /**
     * 保持get的参数和post参数格式统一，加了replace的操作
     * @param req
     * @param apiName
     * @param cookie
     * @return
     */
    public CloseableHttpResponse get(String req,String apiName,Cookie cookie){

        this.setUrl(apiName);
        CloseableHttpResponse response = null;
        String temp= JSONObject.parseObject(req).toString();
        params=temp.replace("{", "").replace("}", "").
                replace("\"", "").replace("'", "").
                replace(":", "=").replace(",", "&");

        String path=getUrl()+"?"+params;
        try {
            response= RestClient.getApi(path,headermap,cookie);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  response;
    }

    public CloseableHttpResponse run(String req,String apiName){
        this.setUrl(apiName);
        CloseableHttpResponse response = null;
        params= JSONObject.parseObject(req).toJSONString();
        try {
            response= RestClient.postApi(url,params,headermap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  response;
    }
public JSONObject convertResponseJson(CloseableHttpResponse response){
    String reStr = null;
    try {
        reStr = EntityUtils.toString(response.getEntity(), "UTF-8");
    } catch (IOException e) {
        e.printStackTrace();
    }
    JSONObject reJson = JSONObject.parseObject(reStr);

    return  reJson;

}
    public String getUrl() {
        return url;
    }
}
