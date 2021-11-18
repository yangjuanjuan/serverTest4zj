package com.zjlab.qa.clientApi;

import com.zjlab.qa.base.ApiBaseClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.cookie.Cookie;

/**
 * 图构建Api
 */
public class GraphBuildClientApi extends ApiBaseClient {



    public static String GRAPH_QUERY_BY_ID="GRAPH_QUERY_BY_ID";
    public static String GRAPH_SEARCH="GRAPH_SEARCH";
    public static String GRAPH_BATCH_UPDATE="GRAPH_BATCH_UPDATE";
    public static String GRAPH_UPDATE_NODE="GRAPH_UPDATE_NODE";
    public static String GRAPH_UPDATE_LINK="GRAPH_UPDATE_LINK";
    public static String GRAPH_QUERY_CLUSTER="GRAPH_QUERY_CLUSTER";
    public static String GRAPH_QUERY_PATH="GRAPH_QUERY_PATH";
    public static String GRAPH_LOAD_TO_ANALYSIS="GRAPH_LOAD_TO_ANALYSIS";
    public static String GRAPH_QUERY_BY_TASK_ID="GRAPH_QUERY_BY_TASK_ID";
    public static String GRAPH_UNDO="GRAPH_UNDO";
    public static String GRAPH_REDO="GRAPH_REDO";
    public static String GRAPH_DOWNLOAD="GRAPH_DOWNLOAD";
    public static String GRAPH_QUERY_ACTION="GRAPH_QUERY_ACTION";
    public static String GRAPH_ADD_ACTION="GRAPH_ADD_ACTION";
    public static String GRAPH_UPDATE_ACTION="GRAPH_UPDATE_ACTION";
    public static String GRAPH_DELETE_ACTION="GRAPH_DELETE_ACTION";
    public static String GRAPH_UPDATE_SEMANTIC="GRAPH_UPDATE_SEMANTIC";
    public static String GRAPH_ADD_CATEGORY="GRAPH_ADD_CATEGORY";
    public static String GRAPH_UPDATE_CATEGORY="GRAPH_UPDATE_CATEGORY";
    public static String GRAPH_ADD_CATEGORY_ATTR="GRAPH_ADD_CATEGORY_ATTR";
    public static String GRAPH_LOAD_GRAPH_DATA="GRAPH_LOAD_GRAPH_DATA";
    public static String GRAPH_REMOVE_CATEGORY_ATTR="GRAPH_REMOVE_CATEGORY_ATTR";
    public static String GRAPH_ADD_EDGE="GRAPH_ADD_EDGE";
    public static String GRAPH_UPDATE_EDGE="GRAPH_UPDATE_EDGE";
    public static String GRAPH_QUERY_EDGE_CONFIGURE="GRAPH_QUERY_EDGE_CONFIGURE";
    public static String GRAPH_SAVE_EDGE_CONFIGURE="GRAPH_SAVE_EDGE_CONFIGURE";
    public static String GRAPH_BATCH_DELETE_CATEGORIES_AND_EDGES="GRAPH_BATCH_DELETE_CATEGORIES_AND_EDGES";


    private LoginClientApi loginClientApi;
    private Cookie cookie;




    public GraphBuildClientApi(){
        loginClientApi=new LoginClientApi();
        loginClientApi.setLoginCookie();
        cookie=loginClientApi.getLoginCookie();

    }
    public CloseableHttpResponse addCategory(String req) {
        CloseableHttpResponse response= this.run(req,GRAPH_ADD_CATEGORY,cookie);
        return  response;
    }
    public CloseableHttpResponse updateCategory(String req) {
        CloseableHttpResponse response= this.run(req,GRAPH_UPDATE_CATEGORY,cookie);
        return  response;
    }
    public CloseableHttpResponse addCategoryAttr(String req) {
        CloseableHttpResponse response= this.run(req,GRAPH_ADD_CATEGORY_ATTR,cookie);
        return  response;
    }
    public CloseableHttpResponse  removeCategoryAttr(String req) {
        CloseableHttpResponse response= this.run(req,GRAPH_REMOVE_CATEGORY_ATTR,cookie);
        return  response;
    }
    public CloseableHttpResponse addEdge(String req) {
        CloseableHttpResponse response= this.run(req,GRAPH_ADD_EDGE,cookie);
        return  response;
    }
    public CloseableHttpResponse updateEdge(String req) {
        CloseableHttpResponse response= this.run(req,GRAPH_UPDATE_EDGE,cookie);
        return  response;
    }
    public CloseableHttpResponse queryEdgeConfigure(String req) {
        CloseableHttpResponse response= this.run(req,GRAPH_QUERY_EDGE_CONFIGURE,cookie);
        return  response;
    }
    public CloseableHttpResponse saveEdgeConfigure(String req) {
        CloseableHttpResponse response= this.run(req,GRAPH_SAVE_EDGE_CONFIGURE,cookie);
        return  response;
    }
    public CloseableHttpResponse batchDeleteCategoriesAndEdges(String req) {
        CloseableHttpResponse response= this.run(req,GRAPH_BATCH_DELETE_CATEGORIES_AND_EDGES,cookie);
        return  response;
    }


    public CloseableHttpResponse loadGraphData(String req) {
        CloseableHttpResponse response= this.run(req,GRAPH_LOAD_GRAPH_DATA,cookie);
        return  response;
    }
    /**
     * 查询视图数据
     * @param
     * @return
     */
    public CloseableHttpResponse queryById(String req) {
        CloseableHttpResponse response= this.run(req,GRAPH_QUERY_BY_ID,cookie);
        return  response;
    }
    /**
     * 查询视图数据
     * @param
     * @return
     */
    public CloseableHttpResponse queryByTaskId(String req) {
        CloseableHttpResponse response= this.run(req,GRAPH_QUERY_BY_TASK_ID,cookie);
        return  response;
    }
    /**
     * 加载视图数据到图分析
     * @param
     * @return
     */
    public CloseableHttpResponse loadToAnalysis(String req) {
        CloseableHttpResponse response= this.run(req,GRAPH_LOAD_TO_ANALYSIS,cookie);
        return  response;
    }
    /**
     * 搜索目标实例节点
     * @param
     * @return
     */
    public CloseableHttpResponse queryCluster(String req) {
        CloseableHttpResponse response= this.run(req,GRAPH_QUERY_CLUSTER,cookie);
        return  response;
    }
    /**
     * 最短路径
     * @param
     * @return
     */
    public CloseableHttpResponse queryPath(String req) {
        CloseableHttpResponse response= this.run(req,GRAPH_QUERY_PATH,cookie);
        return  response;
    }


    public CloseableHttpResponse search(String req) {
        CloseableHttpResponse response= this.run(req,GRAPH_SEARCH,cookie);
        return  response;
    }

    public CloseableHttpResponse batchUpdate(String req) {
        CloseableHttpResponse response= this.run(req,GRAPH_BATCH_UPDATE,cookie);
        return  response;
    }

    public CloseableHttpResponse updateNode(String req) {
        CloseableHttpResponse response= this.run(req,GRAPH_UPDATE_NODE,cookie);
        return  response;
    }

    public CloseableHttpResponse updateLink(String req) {
        CloseableHttpResponse response= this.run(req,GRAPH_UPDATE_LINK,cookie);
        return  response;
    }
    public CloseableHttpResponse queryAction(String req) {
        CloseableHttpResponse response= this.run(req,GRAPH_QUERY_ACTION,cookie);
        return  response;
    }
    public CloseableHttpResponse addAction(String req) {
        CloseableHttpResponse response= this.run(req,GRAPH_ADD_ACTION,cookie);
        return  response;
    }
    public CloseableHttpResponse updateAction(String req) {
        CloseableHttpResponse response= this.run(req,GRAPH_UPDATE_ACTION,cookie);
        return  response;
    }
    public CloseableHttpResponse deleteAction(String req) {
        CloseableHttpResponse response= this.run(req,GRAPH_DELETE_ACTION,cookie);
        return  response;
    }
    public CloseableHttpResponse updateSemantic(String req) {
        CloseableHttpResponse response= this.run(req,GRAPH_UPDATE_SEMANTIC,cookie);
        return  response;
    }

    /**
     * undo
     * @param
     * @return
     */
    public CloseableHttpResponse undo(String req) {
        CloseableHttpResponse response= this.run(req,GRAPH_UNDO,cookie);
        return  response;
    }

    /**
     * undo
     * @param
     * @return
     */
    public CloseableHttpResponse redo(String req) {
        CloseableHttpResponse response= this.run(req,GRAPH_REDO,cookie);
        return  response;
    }
    /**
     * 下载
     * @param
     * @return
     */
    public CloseableHttpResponse download(String req) {
        CloseableHttpResponse response= this.get(req,GRAPH_DOWNLOAD,cookie);
        return  response;
    }

}
