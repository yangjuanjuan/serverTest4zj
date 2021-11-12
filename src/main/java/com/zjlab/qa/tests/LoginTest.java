package com.zjlab.qa.tests;


import com.zjlab.qa.clientApi.LoginClientApi;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LoginTest {

    private LoginClientApi loginClient;

    @BeforeClass
    public void setUp(){
        loginClient = new LoginClientApi();
    }


    @Test
    public void login(){
        String req="{\"name\":\"autoTest\",\"password\":\"000000\"}";
        CloseableHttpResponse response=loginClient.login(req);






    }


}
