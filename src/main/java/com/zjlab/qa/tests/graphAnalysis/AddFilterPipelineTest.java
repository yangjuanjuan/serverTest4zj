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

public class AddFilterPipelineTest {
    private static final Logger log= LoggerFactory.getLogger(AddFilterPipelineTest.class);
    private GraphAnalysisClientApi graphAnalysisClient;
    private List<Map<String, String>> loadData;
    private ProjectManageClientApi projectManageClientApi;
    private String proId;
    private String graphId;
    private String id;//载入的图数据id
    private List<String> proIds;//载入的图数据id



    @BeforeClass
    public void setUp(){
        projectManageClientApi = new ProjectManageClientApi();
        proIds=new ArrayList<String>();
        graphAnalysisClient=new GraphAnalysisClientApi();
        loadData = ReadExcelUtil.getExcelList(GraphAnalysisClientApi.CASE_FILE,GraphAnalysisClientApi.ADD_FILTER_PIPELINE);

    }
    //    通过读取Excel获取测试数据Request Parameter
    @DataProvider
    public Object[][] addFilterPipelineParams(){
        Object[][] files = new Object[loadData.size()][];
        for(int i=0; i<loadData.size(); i++){
            files[i] = new Object[]{loadData.get(i)};
        }
        return files;
    }
    @Test(dataProvider = "addFilterPipelineParams")
    public void addFilterPipeline(Map<?,?> param) throws IOException, InterruptedException {
        String title = (String) param.get("title");
        String params = (String) param.get("params");
        String expectCode = (String) param.get("expectCode");
        String expectMessage = (String) param.get("expectMessage");
        String isRun = (String) param.get("isRun");
        if (isRun.equals("1")) {
            List<String> placeholders = ParseKeyword.getKeywords(params);
            //替换Excel中通过$占位的参数
            if (placeholders.size() > 0 && isGenerateParams(placeholders)) {
                Map<String, String> map = new HashMap<String, String>();
//            新建项目，获取项目id
                JSONObject proJson = projectManageClientApi.convertResponseJson(projectManageClientApi.create());
                proId = JsonHandleUtil.getValueByJpath(proJson, "result");
                proIds.add(proId);

                String addGraph = "{\"projectId\":" + proId + "}";
//        新建标签页，获取标签页ID
                JSONObject graphReJson=graphAnalysisClient.convertResponseJson(graphAnalysisClient.addGraph(addGraph));
                graphId = JsonHandleUtil.getValueByJpath(graphReJson, "result/id");

                String loadParams="{\"projectId\":"+proId+",\"graphId\":"+graphId+",\"fileName\":\"graphTestData.json\"}";
                graphAnalysisClient.loadData(loadParams);
//载入数据需要一些时间
                Thread.sleep(1000);


                map.put("projectId", proId);
                map.put("graphId", graphId);


                params = ParseKeyword.replacePeso(params, map);
            }
            CloseableHttpResponse re = graphAnalysisClient.addFilterPipeline(params);

            log.info("Start Run Test: "+title);
            log.info("Request URL：" + graphAnalysisClient.getUrl() + "，Request Parameter：" + params);
            //获取响应内容
            String loadStatusStr = EntityUtils.toString(re.getEntity(), "UTF-8");
            log.info("Response：" + loadStatusStr);
            //创建JSON对象  把得到的响应字符串 序列化成json对象
            JSONObject resJson = JSONObject.parseObject(loadStatusStr);
            String code = JsonHandleUtil.getValueByJpath(resJson, "code");
            String message = JsonHandleUtil.getValueByJpath(resJson, "message");
            Assert.assertEquals(code, expectCode, title + "; 实际的code：" + code + "，期望返回的code：" + expectCode);

            Assert.assertTrue(message.contains(expectMessage), title + "; 实际的message：" + message + "，期望返回的message：" + expectMessage);


        }

    }

    private boolean isGenerateParams(List<String> p){

        List<String> allParms= Arrays.asList("projectId","graphId");

        return p.containsAll(allParms);

    }


    /**
     * 删除新建的项目
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
