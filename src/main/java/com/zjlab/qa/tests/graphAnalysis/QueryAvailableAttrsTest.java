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
import java.util.*;

public class QueryAvailableAttrsTest {
    private static final Logger log= LoggerFactory.getLogger(QueryAvailableAttrsTest.class);
    private GraphAnalysisClientApi graphAnalysisClient;
    private List<Map<String, String>> queryAvailableAttrsParams;
    private List<String> proIds;
    private List<String> categoryIds;
    private ProjectManageClientApi projectManageClientApi;
    private String proId;
    private String graphId;


    @BeforeClass
    public void setUp(){
        projectManageClientApi =new ProjectManageClientApi();
        graphAnalysisClient=new GraphAnalysisClientApi();
        proIds=new ArrayList<String>();
        categoryIds=new ArrayList<String>();
        queryAvailableAttrsParams = ReadExcelUtil.getExcelList("queryAvailableAttrs.xlsx","");



    }
//    通过读取Excel获取测试数据Request Parameter
    @DataProvider
    public Object[][] queryAvailableAttrsParams(){
        Object[][] files = new Object[queryAvailableAttrsParams.size()][];
        for(int i=0; i<queryAvailableAttrsParams.size(); i++){
            files[i] = new Object[]{queryAvailableAttrsParams.get(i)};
        }
        return files;
    }
    @Test(dataProvider = "queryAvailableAttrsParams")
    public void queryAvailableAttrsTest(Map<?,?> param) throws IOException, InterruptedException {
        String title=(String) param.get("title");
        String params = (String) param.get("params");
        String expectCode = (String) param.get("expectCode");
        String expectMessage = (String) param.get("expectMessage");
        String isRun = (String) param.get("isRun");
        if(isRun.equals("1")) {
            List<String> placeholders = ParseKeyword.getKeywords(params);
//替换Excel中通过$占位的参数
            if (placeholders.size() > 0 &&isGenerateParams(placeholders)) {
                Map<String, String> map = new HashMap<String, String>();
//            新建项目，获取项目id
                JSONObject proJson = projectManageClientApi.convertResponseJson(projectManageClientApi.create());
                proId = JsonHandleUtil.getValueByJpath(proJson, "result");
                proIds.add(proId);

//        新建标签页，获取标签页ID
                String addGraph = "{\"projectId\":" + proId + "}";
                JSONObject graphReJson=graphAnalysisClient.convertResponseJson(graphAnalysisClient.addGraph(addGraph));
                graphId = JsonHandleUtil.getValueByJpath(graphReJson, "result/id");

// 导入数据
                String loadParams="{\"projectId\":"+proId+",\"graphId\":"+graphId+",\"fileName\":\"graphTestData.json\"}";
                graphAnalysisClient.loadData(loadParams);
//载入数据需要一些时间
                Thread.sleep(1000);

//                查询视图数据，获取categoryIds
                String queryParams="{\"projectId\":"+proId+",\"graphId\":"+graphId+"}";
                JSONObject queryReJson=graphAnalysisClient.convertResponseJson(graphAnalysisClient.queryById(queryParams));
                categoryIds.add(JsonHandleUtil.getValueByJpath(queryReJson, "result/categories[0]/id"));



                map.put("projectId", proId);
                map.put("graphId", graphId);
                map.put("categoryIds", categoryIds.toString());
                params = ParseKeyword.replacePeso(params, map);
//            params="{\"projectId\":"+proId+",\"graphId\":"+graphId+"}";
            }

            CloseableHttpResponse re = graphAnalysisClient.queryAvailableAttrs(params);

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

    private boolean isGenerateParams(List<String> p){

        List<String> allParms= Arrays.asList("projectId","graphId","categoryIds");

        return p.containsAll(allParms);

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



