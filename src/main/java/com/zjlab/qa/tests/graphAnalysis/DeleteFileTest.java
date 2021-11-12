package com.zjlab.qa.tests.graphAnalysis;

import com.alibaba.fastjson.JSONObject;
import com.zjlab.qa.clientApi.GraphAnalysisClientApi;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeleteFileTest {
    private static final Logger log= LoggerFactory.getLogger(DeleteFileTest.class);
    private GraphAnalysisClientApi graphAnalysisClient;
    private List<Map<String, String>> deleteFileData;



    @BeforeClass
    public void setUp(){
        graphAnalysisClient=new GraphAnalysisClientApi();
        deleteFileData = ReadExcelUtil.getExcelList("deleteFile.xlsx","");



    }
//    通过读取Excel获取测试数据Request Parameter
    @DataProvider
    public Object[][] deleteFileData(){
        Object[][] files = new Object[deleteFileData.size()][];
        for(int i=0; i<deleteFileData.size(); i++){
            files[i] = new Object[]{deleteFileData.get(i)};
        }
        return files;
    }
    @Test(dataProvider = "deleteFileData")
    public void deleteFileTest(Map<?,?> param) throws IOException, InterruptedException {
        String title=(String) param.get("title");
        String params = (String) param.get("params") ;
        String expectCode = (String) param.get("expectCode");
        String expectMessage = (String) param.get("expectMessage");
        String isRun = (String) param.get("isRun");
        if(isRun.equals("1")) {
            List<String> placeholders = ParseKeyword.getKeywords(params);
            //替换Excel中通过$占位的参数
            if (placeholders.size() > 0 && placeholders.contains("fileName")){
                String fileName="graphDemo.json";
                Map<String, String> map = new HashMap<String, String>();
                map.put("fileName", fileName);
                params = ParseKeyword.replacePeso(params, map);
                graphAnalysisClient.uploadFile(fileName);
                Thread.sleep(1000);
            }
            CloseableHttpResponse re = graphAnalysisClient.deleteFile(params);
            log.info("Start Run Test: "+title);
            //获取响应内容
            String responseString = EntityUtils.toString(re.getEntity(), "UTF-8");
            log.info("Request URL：" + graphAnalysisClient.getUrl() + "，Request Parameter：" + params);
            log.info("Response：" + responseString);
            //创建JSON对象  把得到的响应字符串 序列化成json对象
            JSONObject responseJson = JSONObject.parseObject(responseString);
            String code = JsonHandleUtil.getValueByJpath(responseJson, "code");
            String message = JsonHandleUtil.getValueByJpath(responseJson, "message");
            Assert.assertEquals(code, expectCode, title + "; 实际的code：" + code + "，期望返回的code：" + expectCode);
            Assert.assertTrue(message.contains(expectMessage), title + "; 实际的message：" + message + "，期望返回的message：" + expectMessage);


        }
    }




}
