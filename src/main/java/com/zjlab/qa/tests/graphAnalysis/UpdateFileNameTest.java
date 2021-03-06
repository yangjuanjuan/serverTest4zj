package com.zjlab.qa.tests.graphAnalysis;

import com.alibaba.fastjson.JSON;
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
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.*;

public class UpdateFileNameTest {
    private static final Logger log= LoggerFactory.getLogger(UpdateFileNameTest.class);
    private GraphAnalysisClientApi graphAnalysisClient;
    private List<Map<String, String>> updateFileNameData;
    private List<String> files;



    @BeforeClass
    public void setUp(){
        graphAnalysisClient=new GraphAnalysisClientApi();
        files=new ArrayList<String>();
        updateFileNameData = ReadExcelUtil.getExcelList(GraphAnalysisClientApi.CASE_FILE,GraphAnalysisClientApi.UPDATE_FILE_NAME);



    }
//    通过读取Excel获取测试数据Request Parameter
    @DataProvider
    public Object[][] updateFileNameData(){
        Object[][] files = new Object[updateFileNameData.size()][];
        for(int i=0; i<updateFileNameData.size(); i++){
            files[i] = new Object[]{updateFileNameData.get(i)};
        }
        return files;
    }
    @Test(dataProvider = "updateFileNameData")
    public void updateFileNameTest(Map<?,?> param) throws IOException, InterruptedException {
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
                JSONObject paramsJson= JSON.parseObject(params);
                String rename=paramsJson.getString("update");
                Thread.sleep(1000);
                files.add(fileName);
                files.add(rename);

            }
            CloseableHttpResponse re = graphAnalysisClient.updateFileName(params);
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

    /**
     * 删除上传的文件
     */
    @AfterClass
    public void tearDown(){
        for (String name:files) {
                String param="{\"fileName\":\""+name+"\"}";
                CloseableHttpResponse re= graphAnalysisClient.deleteFile(param);


                String responseString = null;
                try {
                    responseString = EntityUtils.toString(re.getEntity(), "UTF-8");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                log.info("############################################# Start Clear Test Data ##############################################");
                log.info("Request URL："+graphAnalysisClient.getUrl()+"，Request Parameter："+param);
                log.info("Response："+responseString);
                log.info("########################################## Clear Test Data Success ##############################################");


        }
        }






}
