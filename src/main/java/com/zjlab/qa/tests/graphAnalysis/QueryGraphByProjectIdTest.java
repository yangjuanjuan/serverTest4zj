package com.zjlab.qa.tests.graphAnalysis;

import com.alibaba.fastjson.JSONObject;
import com.zjlab.qa.clientApi.GraphAnalysisClientApi;
import com.zjlab.qa.clientApi.ProjectManageClientApi;
import com.zjlab.qa.common.ParseKeyword;
import com.zjlab.qa.utils.JsonHandleUtil;
import com.zjlab.qa.utils.ReadExcelUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryGraphByProjectIdTest {
    private static final Logger log= LoggerFactory.getLogger(QueryGraphByProjectIdTest.class);
    private GraphAnalysisClientApi graphAnalysisClient;
    private List<Map<String, String>> deleteGraphData;
    private ProjectManageClientApi projectManageClientApi;
    private List<String> proIds;
    private String proId;


    @BeforeClass
    public void setUp(){
        projectManageClientApi =new ProjectManageClientApi();
        graphAnalysisClient=new GraphAnalysisClientApi();
        proIds=new ArrayList<String>();
        deleteGraphData = ReadExcelUtil.getExcelList(GraphAnalysisClientApi.CASE_FILE,GraphAnalysisClientApi.QUERY_GRAPH_BY_PROID);



    }
//    通过读取Excel获取测试数据Request Parameter
    @DataProvider
    public Object[][] queryGraphData(){
        Object[][] files = new Object[deleteGraphData.size()][];
        for(int i=0; i<deleteGraphData.size(); i++){
            files[i] = new Object[]{deleteGraphData.get(i)};
        }
        return files;
    }
    @Test(dataProvider = "queryGraphData")
    public void queryGraphByProjectIdTest(Map<?,?> param) throws IOException {
        String title=(String) param.get("title");
        String params = (String) param.get("params");
        String expectCode = (String) param.get("expectCode");
           String expectMessage = (String) param.get("expectMessage");
        String isRun = (String) param.get("isRun");
        if(isRun.equals("1")) {
            List<String> placeholders = ParseKeyword.getKeywords(params);
//替换Excel中通过$占位的参数
            if (placeholders.size() > 0 && placeholders.contains("projectId")) {
                Map<String, String> map = new HashMap<String, String>();
//            新建项目，获取项目id
                CloseableHttpResponse projRe = projectManageClientApi.create();
                String responseStr = EntityUtils.toString(projRe.getEntity(), "UTF-8");
                JSONObject responseJson = JSONObject.parseObject(responseStr);
                proId = JsonHandleUtil.getValueByJpath(responseJson, "result");
                proIds.add(proId);
                map.put("projectId", proId);
                params = ParseKeyword.replacePeso(params, map);

            }

            CloseableHttpResponse re = graphAnalysisClient.queryGraphByProjectId(params);
            log.info("Start Run Test: "+title);
            //获取响应内容
            String queryGraphString = EntityUtils.toString(re.getEntity(), "UTF-8");
            log.info("Request URL：" + graphAnalysisClient.getUrl() + "，Request Parameter：" + params);
            log.info("Response：" + queryGraphString);
            //创建JSON对象  把得到的响应字符串 序列化成json对象
            JSONObject queryGraphJson = JSONObject.parseObject(queryGraphString);
            String code = JsonHandleUtil.getValueByJpath(queryGraphJson, "code");
            String message = JsonHandleUtil.getValueByJpath(queryGraphJson, "message");

            Assert.assertEquals(code, expectCode, title + "; 实际的code：" + code + "，期望返回的code：" + expectCode);
            Assert.assertTrue(message.contains(expectMessage), title + "; 实际的message：" + message + "，期望返回的message：" + expectMessage);

        }
    }

    /**
     * 删除新建项目
     */
    @AfterClass
    public void tearDown(){
        for (String proId:proIds
        ) {
            String delPrams="{\"id\":"+proId+"}";
            CloseableHttpResponse re= projectManageClientApi.deleteById(delPrams);
            String responseString = null;
            try {
                responseString = EntityUtils.toString(re.getEntity(), "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }

            log.info("############################################# Start Clear Test Data ##############################################");
            log.info("Request URL："+ projectManageClientApi.getUrl()+"，Request Parameter："+delPrams);
            log.info("Response："+responseString);
            log.info("########################################## Clear Test Data Success ##############################################");

        }

    }
}



