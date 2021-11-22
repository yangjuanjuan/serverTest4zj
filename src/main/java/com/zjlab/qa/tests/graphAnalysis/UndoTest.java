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

public class UndoTest {
    private static final Logger log= LoggerFactory.getLogger(UndoTest.class);
    private GraphAnalysisClientApi graphAnalysisClient;
    private List<Map<String, String>> undoParams;
    private List<String> proIds;
    private ProjectManageClientApi projectManageClientApi;
    private String proId;
    private String graphId;


    @BeforeClass
    public void setUp(){
        projectManageClientApi =new ProjectManageClientApi();
        graphAnalysisClient=new GraphAnalysisClientApi();
        proIds=new ArrayList<String>();
        undoParams = ReadExcelUtil.getExcelList(GraphAnalysisClientApi.CASE_FILE,GraphAnalysisClientApi.UNDO);



    }
//    通过读取Excel获取测试数据Request Parameter
    @DataProvider
    public Object[][] undoParams(){
        Object[][] files = new Object[undoParams.size()][];
        for(int i=0; i<undoParams.size(); i++){
            files[i] = new Object[]{undoParams.get(i)};
        }
        return files;
    }
    @Test(dataProvider = "undoParams")
    public void undoTest(Map<?,?> param) throws IOException {
        String title=(String) param.get("title");
        String params = (String) param.get("params");
        String expectCode = (String) param.get("expectCode");
        String expectMessage = (String) param.get("expectMessage");
        String isRun = (String) param.get("isRun");
        if(isRun.equals("1")) {
            List<String> placeholders = ParseKeyword.getKeywords(params);
//替换Excel中通过$占位的参数
            if (placeholders.size() > 0 && placeholders.contains("projectId") && placeholders.contains("graphId")) {
                Map<String, String> map = new HashMap<String, String>();
//            新建项目，获取项目id
                CloseableHttpResponse projRe = projectManageClientApi.create();
                String responseStr = EntityUtils.toString(projRe.getEntity(), "UTF-8");
                JSONObject responseJson = JSONObject.parseObject(responseStr);
                proId = JsonHandleUtil.getValueByJpath(responseJson, "result");
                proIds.add(proId);
                String addGraph = "{\"projectId\":" + proId + "}";
//        新建标签页，获取标签页ID
                CloseableHttpResponse graphRe = graphAnalysisClient.addGraph(addGraph);
                String graphReStr = EntityUtils.toString(graphRe.getEntity(), "UTF-8");
                JSONObject graphReJson = JSONObject.parseObject(graphReStr);
                graphId = JsonHandleUtil.getValueByJpath(graphReJson, "result/id");
                map.put("projectId", proId);
                map.put("graphId", graphId);
                params = ParseKeyword.replacePeso(params, map);
//            params="{\"projectId\":"+proId+",\"graphId\":"+graphId+"}";
            }

            CloseableHttpResponse re = graphAnalysisClient.undo(params);

            //获取响应内容
            log.info("Start Run Test: "+title);
            String queryStr = EntityUtils.toString(re.getEntity(), "UTF-8");
            log.info("Request URL：" + graphAnalysisClient.getUrl() + "，Request Parameter：" + params);
            log.info("Response：" + queryStr);
            //创建JSON对象  把得到的响应字符串 序列化成json对象
            JSONObject queryJson = JSONObject.parseObject(queryStr);
            String code = JsonHandleUtil.getValueByJpath(queryJson, "code");
            String message = JsonHandleUtil.getValueByJpath(queryJson, "message");

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



