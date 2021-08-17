package com.zjlab.qa.apiClient;

import com.alibaba.fastjson.JSONObject;
import com.zjlab.qa.base.ApiBaseClient;
import com.zjlab.qa.base.RestClient;
import com.zjlab.qa.utils.GetJsonValueUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.cookie.Cookie;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class GraphAnalysisClientApi extends ApiBaseClient {

    private static String ADD_GRAPH="ADD_GRAPH";
    private static String QUERY_GRAPH="QUERY_GRAPH";
    private static String DELETE_GRAPH_BYID="DELETE_GRAPH_BYID";
    private LoginClientApi loginClientApi;
    private Cookie cookie;




    public GraphAnalysisClientApi(){
        loginClientApi=new LoginClientApi();
        loginClientApi.setLoginCookie();
        cookie=loginClientApi.getLoginCookie();

    }

    public CloseableHttpResponse addGraph(String req) {
        CloseableHttpResponse response= this.run(req,ADD_GRAPH,cookie);
        return  response;
    }

    public CloseableHttpResponse deleteGraphById(String req) {
        CloseableHttpResponse response= this.run(req,DELETE_GRAPH_BYID,cookie);
        return  response;
    }








}
