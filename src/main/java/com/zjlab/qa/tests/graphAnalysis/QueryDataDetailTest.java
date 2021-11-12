package com.zjlab.qa.tests.graphAnalysis;

import com.alibaba.fastjson.JSONObject;
import com.zjlab.qa.clientApi.GraphAnalysisClientApi;
import com.zjlab.qa.clientApi.ProjectManageClientApi;
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

public class QueryDataDetailTest {
    private static final Logger log= LoggerFactory.getLogger(QueryDataDetailTest.class);
    private GraphAnalysisClientApi graphAnalysisClient;
    private List<Map<String, String>> loadData;
    private ProjectManageClientApi projectManageClientApi;



    @BeforeClass
    public void setUp(){
        projectManageClientApi =new ProjectManageClientApi();
        graphAnalysisClient=new GraphAnalysisClientApi();
        loadData = ReadExcelUtil.getExcelList("queryDataDetail.xlsx","");



    }
    //    通过读取Excel获取测试数据Request Parameter
    @DataProvider
    public Object[][] queryDetailParams(){
        Object[][] files = new Object[loadData.size()][];
        for(int i=0; i<loadData.size(); i++){
            files[i] = new Object[]{loadData.get(i)};
        }
        return files;
    }
    @Test(dataProvider = "queryDetailParams")
    public void queryDataDetail(Map<?,?> param) throws IOException {
        String title = (String) param.get("title");
        String params = (String) param.get("params");
        String expectCode = (String) param.get("expectCode");
        String expectMessage = (String) param.get("expectMessage");
        String isRun = (String) param.get("isRun");
        if (isRun.equals("1")) {
            CloseableHttpResponse re = graphAnalysisClient.queryDataDetail(params);
            log.info("Start Run Test: "+title);
            //获取响应内容
            String reStr = EntityUtils.toString(re.getEntity(), "UTF-8");
            log.info("Request URL：" + graphAnalysisClient.getUrl() + "，Request Parameter：" + params);
            log.info("Response：" + reStr);
            //创建JSON对象  把得到的响应字符串 序列化成json对象
            JSONObject resJson = JSONObject.parseObject(reStr);
            String code = JsonHandleUtil.getValueByJpath(resJson, "code");
            String message = JsonHandleUtil.getValueByJpath(resJson, "message");
            Assert.assertEquals(code, expectCode, title + "; 实际的code：" + code + "，期望返回的code：" + expectCode);

            Assert.assertTrue(message.contains(expectMessage), title + "; 实际的message：" + message + "，期望返回的message：" + expectMessage);
        }

    }



}
