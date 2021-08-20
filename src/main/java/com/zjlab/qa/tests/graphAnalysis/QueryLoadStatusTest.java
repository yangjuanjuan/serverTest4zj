package com.zjlab.qa.tests.graphAnalysis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zjlab.qa.apiClient.GraphAnalysisClientApi;
import com.zjlab.qa.apiClient.ProjectManage;
import com.zjlab.qa.common.ParseKeyword;
import com.zjlab.qa.utils.GetJsonValueUtil;
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
import java.util.*;

public class QueryLoadStatusTest {
    private static final Logger log= LoggerFactory.getLogger(QueryLoadStatusTest.class);
    private GraphAnalysisClientApi graphAnalysisClient;
    private List<Map<String, String>> loadData;
    private ProjectManage projectManage;
    private String proId;
    private String graphId;
    private String id;//载入的图数据id
    private List<String> proIds;//载入的图数据id



    @BeforeClass
    public void setUp(){
        projectManage = new ProjectManage();
        proIds=new ArrayList<String>();
        graphAnalysisClient=new GraphAnalysisClientApi();
        loadData = ReadExcelUtil.getExcuteList("queryLoadStatus.xlsx");

    }
    //    通过读取Excel获取测试数据Request Parameter
    @DataProvider
    public Object[][] queryLoadStatusParams(){
        Object[][] files = new Object[loadData.size()][];
        for(int i=0; i<loadData.size(); i++){
            files[i] = new Object[]{loadData.get(i)};
        }
        return files;
    }
    @Test(dataProvider = "queryLoadStatusParams")
    public void queryLoadStatus4Excel(Map<?,?> param) throws IOException {
        String title = (String) param.get("title");
        String params = (String) param.get("params");
        String expectCode = (String) param.get("expectCode");
        String expectMessage = (String) param.get("expectMessage");
        String isRun = (String) param.get("isRun");
        if (isRun.contains("1")) {
            List<String> placeholders = ParseKeyword.getKeywords(params);
            //替换Excel中通过$占位的参数
            if (placeholders.size() > 0 && isGenerateParams(placeholders)) {
                Map<String, String> map = new HashMap<String, String>();
//            新建项目，获取项目id
                JSONObject proJson = projectManage.convertResponseJson(projectManage.create());
                proId = GetJsonValueUtil.getValueByJpath(proJson, "result");
                String addGraph = "{\"projectId\":" + proId + "}";
//        新建标签页，获取标签页ID
                JSONObject graphReJson=graphAnalysisClient.convertResponseJson(graphAnalysisClient.addGraph(addGraph));
                graphId = GetJsonValueUtil.getValueByJpath(graphReJson, "result/id");


                String loadParams="{\"projectId\":"+proId+",\"graphId\":"+graphId+",\"fileName\":\"graphTestData.json\"}";
//    载入图数据，获取数据id

                JSONObject dataIdJson=graphAnalysisClient.convertResponseJson(graphAnalysisClient.loadData(loadParams));
                id = GetJsonValueUtil.getValueByJpath(dataIdJson, "result");


                map.put("projectId", proId);
                map.put("graphId", graphId);
                map.put("id",id);
                params = ParseKeyword.replacePeso(params, map);
            }
            CloseableHttpResponse re = graphAnalysisClient.queryLoadStatus(params);

            log.info("Start Run Test: "+title);
            log.info("Request URL：" + graphAnalysisClient.getUrl() + "，Request Parameter：" + params);
            //获取响应内容
            String loadStatusStr = EntityUtils.toString(re.getEntity(), "UTF-8");
            log.info("Response：" + loadStatusStr);
            //创建JSON对象  把得到的响应字符串 序列化成json对象
            JSONObject resJson = JSONObject.parseObject(loadStatusStr);
            String code = GetJsonValueUtil.getValueByJpath(resJson, "code");
            String message = GetJsonValueUtil.getValueByJpath(resJson, "message");
            Assert.assertEquals(code, expectCode, title + "; 实际的code：" + code + "，期望返回的code：" + expectCode);

            Assert.assertTrue(message.contains(expectMessage), title + "; 实际的message：" + message + "，期望返回的message：" + expectMessage);
            if ("100".equals(code)) {
                JSONObject temp = JSON.parseObject(params);
                proIds.add(temp.getString("projectId"));
            }
            String status=GetJsonValueUtil.getValueByJpath(resJson, "result/status");

            Assert.assertTrue(isInStatus(status), "返回的status："+status+"有误！");




        }

    }

    private boolean isGenerateParams(List<String> p){

        List<String> allParms= Arrays.asList("projectId","graphId","id");

        return p.containsAll(allParms);

    }

    private boolean isInStatus(String s){

        List<String> allStatus= Arrays.asList("SUCCESS","FAIL","RUNNING");

        return allStatus.contains(s);

    }

    /**
     * 删除新建的项目
     */
    @AfterClass
    public void tearDown(){

        for (String proId:proIds
             ) {
            String delPrams="{\"id\":"+proId+"}";
            CloseableHttpResponse re= projectManage.deleteById(delPrams);
            String responseString = null;
            try {
                responseString = EntityUtils.toString(re.getEntity(), "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }

            log.info("############################################# Start Clear Test Data ##############################################");
            log.info("Request URL："+projectManage.getUrl()+"，Request Parameter："+delPrams);
            log.info("Response："+responseString);
            log.info("########################################## Clear Test Data Success ##############################################");

        }

    }

}
