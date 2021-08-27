package com.zjlab.qa.apiClient;

import com.zjlab.qa.base.ApiBaseClient;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.cookie.Cookie;


public class GraphAnalysisClientApi extends ApiBaseClient {

    private static String ADD_GRAPH="ADD_GRAPH";
    private static String DELETE_GRAPH_BY_ID="DELETE_GRAPH_BY_ID";
    private static String QUERY_GRAPH_BY_PROID="QUERY_GRAPH_BY_PROID";
    private static String RENAME_TAB="RENAME_TAB";

    private static String LOAD_DATA="LOAD_DATA";
    private static String QUERY_LOAD_STATUS="QUERY_LOAD_STATUS";


    private static String QUERY_BY_ID="QUERY_BY_ID";
    private static String QUERY_DATA_DETAIL="QUERY_DATA_DETAIL";
    private static String BATCH_UPDATE="BATCH_UPDATE";
    private static String BATCH_DELETE_NODES_AND_LINKS="BATCH_DELETE_NODES_AND_LINKS";



    private static String ADD_FILTER="ADD_FILTER";
    private static String UPDATE_FILTER="UPDATE_FILTER";
    private static String DELETE_FILTER="DELETE_FILTER";


    private static String EXECUTE_FILTER_PIPELINE="EXECUTE_FILTER_PIPELINE";
    private static String RESET_FILTER_PIPELINE="RESET_FILTER_PIPELINE";
    private static String DELETE_FILTER_PIPELINE="DELETE_FILTER_PIPELINE";
    private static String QUERY_FILTER_BY_FILTER_PIPELINE_ID="QUERY_FILTER_BY_FILTER_PIPELINE_ID";

    private static String QUERY_FILTER_AVAILABLE_ATTRS="QUERY_FILTER_AVAILABLE_ATTRS";

    private static String ADD_FILTER_PIPELINE="ADD_FILTER_PIPELINE";
    private static String QUERY_FILTER_PIPELINE_BY_GRAPH_ID="QUERY_FILTER_PIPELINE_BY_GRAPH_ID";
    private static String UPDATE_FILTER_PIPELINE="UPDATE_FILTER_PIPELINE";


    private static String UNDO="UNDO";
    private static String REDO="REDO";

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
     * 重命名标签页
     * @param req
     * @return
     */
    public CloseableHttpResponse renameTab(String req) {
        CloseableHttpResponse response= this.run(req,RENAME_TAB,cookie);
        return  response;
    }


    /**
     * 查询视图数据
     * @param req
     * @return
     */
    public CloseableHttpResponse queryById(String req) {
        CloseableHttpResponse response= this.run(req,QUERY_BY_ID,cookie);
        return  response;
    }

    /**
     * 查询视图数据详情：边-节点
     * @param req
     * @return
     */
    public CloseableHttpResponse queryDataDetail(String req) {
        CloseableHttpResponse response= this.run(req,QUERY_DATA_DETAIL,cookie);
        return  response;
    }

    /**
     * 批量更新数据详情：边-节点
     * @param req
     * @return
     */
    public CloseableHttpResponse batchUpdate(String req) {
        CloseableHttpResponse response= this.run(req,BATCH_UPDATE,cookie);
        return  response;
    }
    /**
     * 批量删除：边-节点
     * @param req
     * @return
     */
    public CloseableHttpResponse batchDeleteNodesAndLinks(String req) {
        CloseableHttpResponse response= this.run(req,BATCH_DELETE_NODES_AND_LINKS,cookie);
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


    /**
     * 查图文件数据load状态
     * @param req
     * @return
     */
    public CloseableHttpResponse queryLoadStatus(String req) {
        CloseableHttpResponse response= this.run(req,QUERY_LOAD_STATUS,cookie);
        return  response;
    }
    /**
     * 新增addFilterPipeline
     * @param req
     * @return
     */
    public CloseableHttpResponse addFilterPipeline(String req) {
        CloseableHttpResponse response= this.run(req,ADD_FILTER_PIPELINE,cookie);
        return  response;
    }
    /**
     * 查询过滤器流程
     * @param req
     * @return
     */
    public CloseableHttpResponse queryFilterPipelineByGraphId(String req) {
        CloseableHttpResponse response= this.run(req,QUERY_FILTER_PIPELINE_BY_GRAPH_ID,cookie);
        return  response;
    }
    /**
     * 查询过滤器流程
     * @param req
     * @return
     */
    public CloseableHttpResponse updateFilterPipeline(String req) {
        CloseableHttpResponse response= this.run(req,UPDATE_FILTER_PIPELINE,cookie);
        return  response;
    }
    /**
     * 执行过滤器流程
     * @param req
     * @return
     */
    public CloseableHttpResponse executeFilterPipeline(String req) {
        CloseableHttpResponse response= this.run(req,EXECUTE_FILTER_PIPELINE,cookie);
        return  response;
    }
    /**
     * 取消执行过滤器
     * @param req
     * @return
     */
    public CloseableHttpResponse resetFilterPipeline(String req) {
        CloseableHttpResponse response= this.run(req,RESET_FILTER_PIPELINE,cookie);
        return  response;
    }

    /**
     * 删除过滤器流程
     * @param req
     * @return
     */
    public CloseableHttpResponse deleteFilterPipeline(String req) {
        CloseableHttpResponse response= this.run(req,DELETE_FILTER_PIPELINE,cookie);
        return  response;
    }

    /**
     * 查询过滤器可用属性 queryFilterAvailableAttrs
     * @param req
     * @return
     */
    public CloseableHttpResponse queryFilterAvailableAttrs(String req) {
        CloseableHttpResponse response= this.run(req,QUERY_FILTER_AVAILABLE_ATTRS,cookie);
        return  response;
    }


    /**
     * 新增addFilter
     * @param req
     * @return
     */
    public CloseableHttpResponse addFilter(String req) {
        CloseableHttpResponse response= this.run(req,ADD_FILTER,cookie);
        return  response;
    }
    /**
     * 新增addFilter
     * @param req
     * @return
     */
    public CloseableHttpResponse updateFilter(String req) {
        CloseableHttpResponse response= this.run(req,UPDATE_FILTER,cookie);
        return  response;
    }

    /**
     *
     * @param req
     * @return
     */
    public CloseableHttpResponse deleteFilter(String req) {
        CloseableHttpResponse response= this.run(req,DELETE_FILTER,cookie);
        return  response;
    }


    /**
     * 查询过滤器流程中的过滤器
     * @param req
     * @return
     */
    public CloseableHttpResponse queryFilterByFilterPipelineId(String req) {
        CloseableHttpResponse response= this.run(req,QUERY_FILTER_BY_FILTER_PIPELINE_ID,cookie);
        return  response;
    }
    /**
     * 工具栏-撤销
     * @param req
     * @return
     */
    public CloseableHttpResponse undo(String req) {
        CloseableHttpResponse response= this.run(req,UNDO,cookie);
        return  response;
    }

    /**
     * 工具栏-重做
     * @param req
     * @return
     */
    public CloseableHttpResponse redo(String req) {
        CloseableHttpResponse response= this.run(req,REDO,cookie);
        return  response;
    }
}
