package com.zjlab.qa.clientApi;

import com.zjlab.qa.base.ApiBaseClient;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.cookie.Cookie;



public class GraphAnalysisClientApi extends ApiBaseClient {
     public static String CASE_FILE="graphAnalysisCase.xlsx";
     public static String UPLOAD_FILE ="UPLOAD_FILE";
     public static String DELETE_FILE ="DELETE_FILE";
     public static String PREVIEW_FILE ="PREVIEW_FILE";
     public static String LIST_FILES ="LIST_FILES";
     public static String QUERY_FILE_BY_NAME ="QUERY_FILE_BY_NAME";
     public static String UPDATE_FILE_NAME ="UPDATE_FILE_NAME";


     public static String ADD_GRAPH="ADD_GRAPH";
     public static String DELETE_GRAPH_BY_ID="DELETE_GRAPH_BY_ID";
     public static String QUERY_GRAPH_BY_PROID="QUERY_GRAPH_BY_PROID";
     public static String RENAME_TAB="RENAME_TAB";

     public static String LOAD_DATA="LOAD_DATA";
     public static String QUERY_LOAD_STATUS="QUERY_LOAD_STATUS";


     public static String QUERY_BY_ID="QUERY_BY_ID";
     public static String QUERY_DATA_DETAIL="QUERY_DATA_DETAIL";
     public static String BATCH_UPDATE="BATCH_UPDATE";
     public static String BATCH_DELETE_NODES_AND_LINKS="BATCH_DELETE_NODES_AND_LINKS";


     public static String ADD_FILTER="ADD_FILTER";
     public static String UPDATE_FILTER="UPDATE_FILTER";
     public static String DELETE_FILTER="DELETE_FILTER";
     public static String EXECUTE_FILTER_PIPELINE="EXECUTE_FILTER_PIPELINE";
     public static String RESET_FILTER_PIPELINE="RESET_FILTER_PIPELINE";
     public static String DELETE_FILTER_PIPELINE="DELETE_FILTER_PIPELINE";
     public static String QUERY_FILTER_BY_FILTER_PIPELINE_ID="QUERY_FILTER_BY_FILTER_PIPELINE_ID";
     public static String QUERY_FILTER_AVAILABLE_ATTRS="QUERY_FILTER_AVAILABLE_ATTRS";
     public static String ADD_FILTER_PIPELINE="ADD_FILTER_PIPELINE";
     public static String QUERY_FILTER_PIPELINE_BY_GRAPH_ID="QUERY_FILTER_PIPELINE_BY_GRAPH_ID";
     public static String UPDATE_FILTER_PIPELINE="UPDATE_FILTER_PIPELINE";



     public static String QUERY_ATTR_STYLE="QUERY_ATTR_STYLE";
     public static String QUERY_AVAILABLE_CATEGORY="QUERY_AVAILABLE_CATEGORY";
     public static String QUERY_AVAILABLE_ATTRS="QUERY_AVAILABLE_ATTRS";


     public static String QUERY_METRICS="QUERY_METRICS";
     public static String QUERY_PAGE_RANK="QUERY_PAGE_RANK";
     public static String QUERY_CLUSTER="QUERY_CLUSTER";
     public static String ACTIVATE_METRIC="ACTIVATE_METRIC";
     public static String EXECUTE_METRICS="EXECUTE_METRICS";


     public static String SEARCH="SEARCH";
     public static String QUERY_PATH="QUERY_PATH";
     public static String QUERY_HEAT_MAP="QUERY_HEAT_MAP";
     public static String DOWNLOAD="DOWNLOAD";
     public static String SAVE_WIDGET="SAVE_WIDGET";
     public static String UNDO="UNDO";
     public static String REDO="REDO";

     private LoginClientApi loginClientApi;
     private Cookie cookie;




    public GraphAnalysisClientApi(){
        loginClientApi=new LoginClientApi();
        loginClientApi.setLoginCookie();
        cookie=loginClientApi.getLoginCookie();

    }

    /**
     * ??????????????? uploadFile
     * @param fileName
     * @return
     */
    public CloseableHttpResponse uploadFile(String fileName) {
        String file= GraphAnalysisClientApi.class.getResource("/fileData/").getPath()+fileName;
        CloseableHttpResponse response= this.uploadFile(file,null,UPLOAD_FILE,cookie);
        return  response;
    }
    /**
     * ???????????????????????? previewFile
     * @param fileName
     * @return
     */
    public CloseableHttpResponse previewFile(String fileName) {
        String file= GraphAnalysisClientApi.class.getResource("/fileData/").getPath()+fileName;
        CloseableHttpResponse response= this.uploadFile(file,null,PREVIEW_FILE,cookie);
        return  response;
    }
    /**
     * updateFileName ?????????
     * @param req
     * @return
     */
    public CloseableHttpResponse updateFileName(String req) {
        CloseableHttpResponse response= this.run(req,UPDATE_FILE_NAME,cookie);
        return  response;
    }
    /**
     * ????????????????????????????????????????????????????????????
     * @param req
     * @return
     */
    public CloseableHttpResponse queryFileByName(String req) {
        CloseableHttpResponse response= this.run(req,QUERY_FILE_BY_NAME,cookie);
        return  response;
    }

    /**
     * ??????????????????
     * @param req
     * @return
     */
    public CloseableHttpResponse listFiles(String req) {
        CloseableHttpResponse response= this.run(req,LIST_FILES,cookie);
        return  response;
    }
    /**
     * deleteFile
     * @param req
     * @return
     */
    public CloseableHttpResponse deleteFile(String req) {
        CloseableHttpResponse response= this.run(req,DELETE_FILE,cookie);
        return  response;
    }

    /**
     * ???????????????
     * @param req
     * @return
     */
    public CloseableHttpResponse addGraph(String req) {
        CloseableHttpResponse response= this.run(req,ADD_GRAPH,cookie);
        return  response;
    }
    /**
     * ???????????????
     * @param req
     * @return
     */
    public CloseableHttpResponse deleteGraphById(String req) {
        CloseableHttpResponse response= this.run(req,DELETE_GRAPH_BY_ID,cookie);
        return  response;
    }
    /**
     * ???????????????
     * @param req
     * @return
     */
    public CloseableHttpResponse queryGraphByProjectId(String req) {
        CloseableHttpResponse response= this.run(req,QUERY_GRAPH_BY_PROID,cookie);
        return  response;
    }

    /**
     * ??????????????????
     * @param req
     * @return
     */
    public CloseableHttpResponse renameTab(String req) {
        CloseableHttpResponse response= this.run(req,RENAME_TAB,cookie);
        return  response;
    }

    /**
     * load???????????????
     * @param req
     * @return
     */
    public CloseableHttpResponse loadData(String req) {
        CloseableHttpResponse response= this.run(req,LOAD_DATA,cookie);
        return  response;
    }


    /**
     * ??????????????????load??????
     * @param req
     * @return
     */
    public CloseableHttpResponse queryLoadStatus(String req) {
        CloseableHttpResponse response= this.run(req,QUERY_LOAD_STATUS,cookie);
        return  response;
    }


    /**
     * ??????????????????
     * @param req
     * @return
     */
    public CloseableHttpResponse queryById(String req) {
        CloseableHttpResponse response= this.run(req,QUERY_BY_ID,cookie);
        return  response;
    }

    /**
     * ??????????????????????????????-??????
     * @param req
     * @return
     */
    public CloseableHttpResponse queryDataDetail(String req) {
        CloseableHttpResponse response= this.run(req,QUERY_DATA_DETAIL,cookie);
        return  response;
    }

    /**
     * ??????????????????????????????-??????
     * @param req
     * @return
     */
    public CloseableHttpResponse batchUpdate(String req) {
        CloseableHttpResponse response= this.run(req,BATCH_UPDATE,cookie);
        return  response;
    }
    /**
     * ??????????????????-??????
     * @param req
     * @return
     */
    public CloseableHttpResponse batchDeleteNodesAndLinks(String req) {
        CloseableHttpResponse response= this.run(req,BATCH_DELETE_NODES_AND_LINKS,cookie);
        return  response;
    }


    /**
     * ??????addFilterPipeline
     * @param req
     * @return
     */
    public CloseableHttpResponse addFilterPipeline(String req) {
        CloseableHttpResponse response= this.run(req,ADD_FILTER_PIPELINE,cookie);
        return  response;
    }
    /**
     * ?????????????????????
     * @param req
     * @return
     */
    public CloseableHttpResponse queryFilterPipelineByGraphId(String req) {
        CloseableHttpResponse response= this.run(req,QUERY_FILTER_PIPELINE_BY_GRAPH_ID,cookie);
        return  response;
    }
    /**
     * ?????????????????????
     * @param req
     * @return
     */
    public CloseableHttpResponse updateFilterPipeline(String req) {
        CloseableHttpResponse response= this.run(req,UPDATE_FILTER_PIPELINE,cookie);
        return  response;
    }
    /**
     * ?????????????????????
     * @param req
     * @return
     */
    public CloseableHttpResponse executeFilterPipeline(String req) {
        CloseableHttpResponse response= this.run(req,EXECUTE_FILTER_PIPELINE,cookie);
        return  response;
    }
    /**
     * ?????????????????????
     * @param req
     * @return
     */
    public CloseableHttpResponse resetFilterPipeline(String req) {
        CloseableHttpResponse response= this.run(req,RESET_FILTER_PIPELINE,cookie);
        return  response;
    }

    /**
     * ?????????????????????
     * @param req
     * @return
     */
    public CloseableHttpResponse deleteFilterPipeline(String req) {
        CloseableHttpResponse response= this.run(req,DELETE_FILTER_PIPELINE,cookie);
        return  response;
    }

    /**
     * ??????????????????????????? queryFilterAvailableAttrs
     * @param req
     * @return
     */
    public CloseableHttpResponse queryFilterAvailableAttrs(String req) {
        CloseableHttpResponse response= this.run(req,QUERY_FILTER_AVAILABLE_ATTRS,cookie);
        return  response;
    }


    /**
     * ??????addFilter
     * @param req
     * @return
     */
    public CloseableHttpResponse addFilter(String req) {
        CloseableHttpResponse response= this.run(req,ADD_FILTER,cookie);
        return  response;
    }
    /**
     * ??????addFilter
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
     * ????????????????????????????????????
     * @param req
     * @return
     */
    public CloseableHttpResponse queryFilterByFilterPipelineId(String req) {
        CloseableHttpResponse response= this.run(req,QUERY_FILTER_BY_FILTER_PIPELINE_ID,cookie);
        return  response;
    }

    /**
     * ??????????????????????????????????????????????????????
     * @param req
     * @return
     */
    public CloseableHttpResponse queryAttrStyle(String req) {
        CloseableHttpResponse response= this.run(req,QUERY_ATTR_STYLE,cookie);
        return  response;
    }

     /**
     * ??????????????????/??? ?????? ?????????????????????????????????
     * @param req
     * @return
     */
    public CloseableHttpResponse queryAvailableCategory(String req) {
        CloseableHttpResponse response= this.run(req,QUERY_AVAILABLE_CATEGORY,cookie);
        return  response;
    }



    /**
     * ????????????????????????????????????????????????????????????????????????????????????
     * @param req
     * @return
     */
    public CloseableHttpResponse queryAvailableAttrs(String req) {
        CloseableHttpResponse response= this.run(req,QUERY_AVAILABLE_ATTRS,cookie);
        return  response;
    }



    /**
     * ??????????????????
     * @param req
     * @return
     */
    public CloseableHttpResponse queryMetrics(String req) {
        CloseableHttpResponse response= this.run(req,QUERY_METRICS,cookie);
        return  response;
    }
    /**
     * ??????????????????
     * @param req
     * @return
     */
    public CloseableHttpResponse queryCluster(String req) {
        CloseableHttpResponse response= this.run(req,QUERY_CLUSTER,cookie);
        return  response;
    }
    /**
     * ??????/??????
     * @param req
     * @return
     */
    public CloseableHttpResponse activateMetric(String req) {
        CloseableHttpResponse response= this.run(req,ACTIVATE_METRIC,cookie);
        return  response;
    }
    /**
     * ??????????????????
     * @param req
     * @return
     */
    public CloseableHttpResponse executeMetrics(String req) {
        CloseableHttpResponse response= this.run(req,EXECUTE_METRICS,cookie);
        return  response;
    }
    /**
     * pageRank??????
     * @param req
     * @return
     */
    public CloseableHttpResponse queryPageRank(String req) {
        CloseableHttpResponse response= this.run(req,QUERY_PAGE_RANK,cookie);
        return  response;
    }

    /**
     *
     * @param req
     * @return
     */
    public CloseableHttpResponse search(String req) {
        CloseableHttpResponse response= this.run(req,SEARCH,cookie);
        return  response;
    }
    /**
     *
     * @param req
     * @return
     */
    public CloseableHttpResponse queryPath(String req) {
    CloseableHttpResponse response= this.run(req,QUERY_PATH,cookie);
    return  response;
    }
    /**
     * @param req
     * @return
     */
    public CloseableHttpResponse queryHeatMap(String req) {
    CloseableHttpResponse response= this.run(req,QUERY_HEAT_MAP,cookie);
    return  response;
    }
    /**
     * @param req
     * @return
     */
    public CloseableHttpResponse download(String req) {
    CloseableHttpResponse response= this.get(req,DOWNLOAD,cookie);
    return  response;
    }
    /**
     * @param req
     * @return
     */
    public CloseableHttpResponse saveWidget(String req) {
    CloseableHttpResponse response= this.run(req,SAVE_WIDGET,cookie);
    return  response;
    }

    /**
     * ?????????-??????
     * @param req
     * @return
     */
    public CloseableHttpResponse undo(String req) {
        CloseableHttpResponse response= this.run(req,UNDO,cookie);
        return  response;
    }

    /**
     * ?????????-??????
     * @param req
     * @return
     */
    public CloseableHttpResponse redo(String req) {
        CloseableHttpResponse response= this.run(req,REDO,cookie);
        return  response;
    }
}
