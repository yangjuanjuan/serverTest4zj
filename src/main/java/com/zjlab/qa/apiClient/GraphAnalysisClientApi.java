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
    private static String DELETE_GRAPH_BY_ID="DELETE_GRAPH_BY_ID";
    private static String QUERY_GRAPH_BY_PROID="QUERY_GRAPH_BY_PROID";

    private static String QUERY_BY_ID="QUERY_BY_ID";
    private static String QUERY_DATA_DETAIL="QUERY_DATA_DETAIL";

    private static String LOAD_DATA="LOAD_DATA";
    private static String QUERY_LOAD_STATUS="QUERY_LOAD_STATUS";


    private static String ADD_FILTER="ADD_FILTER";


    private LoginClientApi loginClientApi;
    private Cookie cookie;




    public GraphAnalysisClientApi(){
        loginClientApi=new LoginClientApi();
        loginClientApi.setLoginCookie();
        cookie=loginClientApi.getLoginCookie();

    }

    /**
     * 新增标签页
     * @param req
     * @return
     */
    public CloseableHttpResponse addGraph(String req) {
        CloseableHttpResponse response= this.run(req,ADD_GRAPH,cookie);
        return  response;
    }
    /**
     * 删除标签页
     * @param req
     * @return
     */
    public CloseableHttpResponse deleteGraphById(String req) {
        CloseableHttpResponse response= this.run(req,DELETE_GRAPH_BY_ID,cookie);
        return  response;
    }
    /**
     * 查询标签页
     * @param req
     * @return
     */
    public CloseableHttpResponse queryGraphByProjectId(String req) {
        CloseableHttpResponse response= this.run(req,QUERY_GRAPH_BY_PROID,cookie);
        return  response;
    }


    /**
     * 查询视图
     * @param req
     * @return
     */
    public CloseableHttpResponse queryById(String req) {
        CloseableHttpResponse response= this.run(req,QUERY_GRAPH_BY_PROID,cookie);
        return  response;
    }

    /**
     * load图文件数据
     * @param req
     * @return
     */
    public CloseableHttpResponse loadData(String req) {
        CloseableHttpResponse response= this.run(req,LOAD_DATA,cookie);
        return  response;
    }



}
