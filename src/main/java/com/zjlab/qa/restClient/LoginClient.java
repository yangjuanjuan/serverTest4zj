package com.zjlab.qa.restClient;


import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.IOException;
import java.util.HashMap;

public class LoginClient {
    private static HashMap<String,String> headermap =new HashMap<String, String>();
    private static JSONObject request = new JSONObject();
    static {
        headermap.put("Content-type","application/json");

    }



    public  static CloseableHttpResponse login(String name,String password,String login_url) {
        CloseableHttpResponse response = null;
        request.put("name",name);
        request.put("password",password);
        try {
            response=RestClient.postApi(login_url,request.toJSONString(),headermap);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return response;
    }
    public static String loginOut(String login_out_url){
        return "";
    }
}
