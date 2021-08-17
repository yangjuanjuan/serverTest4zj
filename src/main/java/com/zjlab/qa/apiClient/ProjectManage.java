package com.zjlab.qa.apiClient;

import com.alibaba.fastjson.JSONObject;
import com.zjlab.qa.base.ApiBaseClient;
import com.zjlab.qa.base.RestClient;
import com.zjlab.qa.utils.GetJsonValueUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.cookie.Cookie;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class ProjectManage extends ApiBaseClient {
    private static String CREAT="CREAT";
    private static String QUERY="QUERY";
    private static String DELETEBYID="DELETE_BY_ID";
    private LoginClientApi loginClientApi;
    private Cookie cookie;

    public ProjectManage(){
        loginClientApi=new LoginClientApi();
        loginClientApi.setLoginCookie();
        cookie=loginClientApi.getLoginCookie();

    }

    public CloseableHttpResponse create(String req) {

        CloseableHttpResponse response= this.run(req,CREAT,cookie);
        return  response;
    }
    public CloseableHttpResponse create() {
        String name= "AutoTest"+RandomStringUtils.randomAlphanumeric(3);
        String req = "{\"name\":"+name+",\"description\":\"\"}";
        CloseableHttpResponse response= this.run(req,CREAT,cookie);
        return  response;
    }

    public CloseableHttpResponse query(String req) {
        CloseableHttpResponse response= this.run(req,QUERY,cookie);
        return  response;
    }

    public CloseableHttpResponse deleteById(String req) {
        CloseableHttpResponse response= this.run(req,DELETEBYID,cookie);
        return  response;
    }

    public Cookie getCookie(){

        return cookie;
    }

    public String getProjectId(){
        CloseableHttpResponse projectRes=this.create();
        String id="";
        try {
            JSONObject projectJson = JSONObject.parseObject(EntityUtils.toString(projectRes.getEntity(),"UTF-8"));
            id = GetJsonValueUtil.getValueByJpath(projectJson,"result");
            return id;
        } catch (IOException e) {
            e.printStackTrace();
        }
       return id;
    }


    public static void main(String[] args) throws IOException {
        String req = "{\"name\":\"tew\",\"description\":\"\"}";
        ProjectManage projectManage= new ProjectManage();
        CloseableHttpResponse re= projectManage.create(req);
        //获取响应内容
        String responseString = EntityUtils.toString(re.getEntity(),"UTF-8");
        JSONObject responseJson = JSONObject.parseObject(responseString);
        System.out.println(responseString);
        String id = GetJsonValueUtil.getValueByJpath(responseJson,"result");
        String del= "{\"id\":"+id+"}";
        CloseableHttpResponse response1= projectManage.deleteById(del);
        //获取响应内容
        String response = EntityUtils.toString(response1.getEntity(),"UTF-8");
        System.out.println(response);
        JSONObject responseJson2 = JSONObject.parseObject(response);
        String result = GetJsonValueUtil.getValueByJpath(responseJson2,"result");

    }

}
