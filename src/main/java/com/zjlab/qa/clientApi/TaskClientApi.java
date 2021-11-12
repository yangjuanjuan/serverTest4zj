package com.zjlab.qa.clientApi;

import com.zjlab.qa.base.ApiBaseClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.cookie.Cookie;


public class TaskClientApi extends ApiBaseClient {



    private static String COMPLEX_LOAD="COMPLEX_LOAD";


    private LoginClientApi loginClientApi;
    private Cookie cookie;




    public TaskClientApi(){
        loginClientApi=new LoginClientApi();
        loginClientApi.setLoginCookie();
        cookie=loginClientApi.getLoginCookie();

    }
    /**
     * 添加到pipeline
     * @param
     * @return
     */
    public CloseableHttpResponse complexLoad(String req) {
        CloseableHttpResponse response= this.run(req,COMPLEX_LOAD,cookie);
        return  response;
    }
}
