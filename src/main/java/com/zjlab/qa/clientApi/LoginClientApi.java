package com.zjlab.qa.clientApi;


import com.zjlab.qa.base.ApiBaseClient;
import com.zjlab.qa.base.RestClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.cookie.Cookie;

public class LoginClientApi extends ApiBaseClient {
    private static String LOGIN_PATH="LOGIN_PATH";
    private static String LOGIN_COOKIE_NAME="rememberMe";
    private Cookie loginCookie;



    public CloseableHttpResponse login(String req) {

        CloseableHttpResponse response= this.run(req,LOGIN_PATH);
        return  response;
    }

    /**
     * 默认账号登录
     * @return
     */
    public CloseableHttpResponse login() {
        String req="{\"name\":\"autoTest\",\"password\":\"000000\"}";
        CloseableHttpResponse response = this.login(req);
        return  response;
    }

    public Cookie getCookieByName(String loginCookieName){

        return RestClient.getCookieByName(LOGIN_COOKIE_NAME);
    }
/**
 *  另外一种方式获取cookie
    public String getLoginCookies(){
        Header [] headers=RestClient.getHeaders();
        String temp = null;
        if(headers != null){
            if(headers.length>1){
                temp=headers[1].getValue();
            }else {
                temp=headers[0].getValue();
            }

        }
        return temp;
    }**/

    /**
     * 设置默认cookie
     */
    public void setLoginCookie(){
       this.login();
       loginCookie=this.getCookieByName("rememberMe");
    }

    /**
     * 获取默认cookie
     */

    public Cookie getLoginCookie(){
        return loginCookie;
    }
/**

public static void main(String[] args) {
    LoginClientApi loginClientApi = new LoginClientApi();
    String req="{\"name\":\"autoTest\",\"password\":\"000000\"}";
    CloseableHttpResponse response=loginClientApi.login(req);
    String nm=loginClientApi.getCookieByName();

}
 **/
}
