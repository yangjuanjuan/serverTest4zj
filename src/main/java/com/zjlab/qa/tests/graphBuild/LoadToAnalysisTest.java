package com.zjlab.qa.tests.graphBuild;

import com.zjlab.qa.clientApi.GraphAnalysisClientApi;
import com.zjlab.qa.clientApi.GraphBuildClientApi;
import com.zjlab.qa.common.ParseKeyword;
import com.zjlab.qa.utils.JsonHandleUtil;
import com.zjlab.qa.utils.ReadExcelUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class LoadToAnalysisTest {
    private static final Logger log= LoggerFactory.getLogger(LoadToAnalysisTest.class);
    private GraphBuildClientApi graphBuildClientApi;
    private List<Map<String, String>> caseParams;

    /**
     * 前期先直接在自动化测试项目中构建好一个图构建的结果
     */
    @BeforeClass
    public void setUp(){
        graphBuildClientApi=new GraphBuildClientApi();
        caseParams = ReadExcelUtil.getExcelList("graphBuildCase.xlsx",GraphBuildClientApi.GRAPH_LOAD_TO_ANALYSIS);

    }

    //    通过读取Excel获取测试数据Request Parameter
    @DataProvider
    public Object[][] caseParams(){
        Object[][] files = new Object[caseParams.size()][];
        for(int i=0; i<caseParams.size(); i++){
            files[i] = new Object[]{caseParams.get(i)};
        }
        return files;
    }
    @Test(dataProvider = "caseParams")
    public void loadToAnalysis(Map<?,?> param) throws IOException, InterruptedException {
        String title = (String) param.get("title");
        String params = (String) param.get("params");
        String expectResult = (String) param.get("expectResult");
        String isRun = (String) param.get("isRun");
        if (isRun.equals("1")) {
            List<String> placeholders = ParseKeyword.getKeywords(params);
            CloseableHttpResponse re = graphBuildClientApi.loadToAnalysis(params);

            log.info("Start Run Test: "+title);
            log.info("Request URL：" + graphBuildClientApi.getUrl() + "，Request Parameter：" + params);
            //获取响应内容
            String res = EntityUtils.toString(re.getEntity(), "UTF-8");
            log.info("Response：" + res);
            String code =JsonHandleUtil.getValueByJpath(JsonHandleUtil.str2Json(res),"code");
            Assert.assertTrue(JsonHandleUtil.strCompare(res,expectResult,"code"));
            Assert.assertTrue(JsonHandleUtil.strCompare(res,expectResult,"message"));

            //若载入成功，需要在图分析视图中将其删除，避免产生过多的测试数据
            if(code.equals("100")){
                boolean flag=true;
                while (flag) {
                    String graphId = JsonHandleUtil.getValueByJpath(JsonHandleUtil.str2Json(res), "result/graphId");
                    String projectId = JsonHandleUtil.getValueByJpath(JsonHandleUtil.str2Json(params), "projectId");
                    log.info("############################################# Start Clear Test Data ##############################################");
                    String req = "{\"projectId\":" + projectId + ",\"graphId\":" + graphId + "}";
                    GraphAnalysisClientApi graphAnalysisClientApi = new GraphAnalysisClientApi();
                    CloseableHttpResponse deleteGraph = graphAnalysisClientApi.deleteGraphById(req);
                    String delRes=EntityUtils.toString(deleteGraph.getEntity(), "UTF-8");
                    log.info("Request URL：" + graphAnalysisClientApi.getUrl() + "，Request Parameter：" + req);
                    log.info("Response：" + delRes);
                    String status =JsonHandleUtil.getValueByJpath(JsonHandleUtil.str2Json(delRes),"code");
                    if(status.equals("100")){
                        flag=false;
                    }

                }

            }

        }

    }

}
