package com.zjlab.qa.clientApi;

import com.alibaba.fastjson.JSONObject;
import com.zjlab.qa.base.ApiBaseClient;
import com.zjlab.qa.utils.JsonHandleUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.cookie.Cookie;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class ProjectManageClientApi extends ApiBaseClient {
    private static String CREAT="CREAT";
    private static String QUERY="QUERY";
    private static String DELETE_BY_ID="DELETE_BY_ID";
    private static String DATASETS_CREAT="DATASETS_CREAT";
    private LoginClientApi loginClientApi;
    private Cookie cookie;

    public ProjectManageClientApi(){
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
        String req = "{\"name\":\""+name+"\",\"description\":\"\"}";
        CloseableHttpResponse response= this.run(req,CREAT,cookie);
        return  response;
    }

    public CloseableHttpResponse query(String req) {
        CloseableHttpResponse response= this.run(req,QUERY,cookie);
        return  response;
    }

    public CloseableHttpResponse deleteById(String req) {
        CloseableHttpResponse response= this.run(req,DELETE_BY_ID,cookie);
        return  response;
    }

    public CloseableHttpResponse datasetsCreate(String req) {
        CloseableHttpResponse response= this.run(req,DATASETS_CREAT,cookie);
        return  response;
    }

    public String getProjectId(){
        CloseableHttpResponse projectRes=this.create();
        String id="";
        try {
            JSONObject projectJson = JSONObject.parseObject(EntityUtils.toString(projectRes.getEntity(),"UTF-8"));
            id = JsonHandleUtil.getValueByJpath(projectJson,"result");
            return id;
        } catch (IOException e) {
            e.printStackTrace();
        }
       return id;
    }


    public static void main(String[] args) throws IOException {
        String req = "{\"name\":\"tew\",\"description\":\"\"}";
        ProjectManageClientApi projectManageClientApi = new ProjectManageClientApi();
        CloseableHttpResponse re= projectManageClientApi.create(req);
        //获取响应内容
        String responseString = EntityUtils.toString(re.getEntity(),"UTF-8");
        JSONObject responseJson = JSONObject.parseObject(responseString);
        System.out.println(responseString);
        String id = JsonHandleUtil.getValueByJpath(responseJson,"result");
        String del= "{\"id\":"+id+"}";
        CloseableHttpResponse response1= projectManageClientApi.deleteById(del);
        //获取响应内容
        String response = EntityUtils.toString(response1.getEntity(),"UTF-8");
        System.out.println(response);
        JSONObject responseJson2 = JSONObject.parseObject(response);
        String result = JsonHandleUtil.getValueByJpath(responseJson2,"result");

    }

}
