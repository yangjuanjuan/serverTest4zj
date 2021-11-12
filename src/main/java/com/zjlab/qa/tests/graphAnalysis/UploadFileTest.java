package com.zjlab.qa.tests.graphAnalysis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zjlab.qa.clientApi.GraphAnalysisClientApi;
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
import java.util.List;
import java.util.Map;

public class UploadFileTest {
    private static final Logger log= LoggerFactory.getLogger(UploadFileTest.class);
    private GraphAnalysisClientApi graphAnalysisClient;
    private List<Map<String, String>> uploadFileData;
    private List<String> files;



    @BeforeClass
    public void setUp(){
        graphAnalysisClient=new GraphAnalysisClientApi();
        files=new ArrayList<String>();
        uploadFileData = ReadExcelUtil.getExcelList("uploadFile.xlsx","");



    }
//    通过读取Excel获取测试数据Request Parameter
    @DataProvider
    public Object[][] uploadFileData(){
        Object[][] files = new Object[uploadFileData.size()][];
        for(int i=0; i<uploadFileData.size(); i++){
            files[i] = new Object[]{uploadFileData.get(i)};
        }
        return files;
    }
    @Test(dataProvider = "uploadFileData")
    public void uploadFileTest(Map<?,?> param) throws IOException {
        String title=(String) param.get("title");
        JSONObject params = JSON.parseObject((String) param.get("params")) ;
        String fileName =params.getString("file");
        String expectCode = (String) param.get("expectCode");
        String expectMessage = (String) param.get("expectMessage");
        String isRun = (String) param.get("isRun");
        if(isRun.equals("1")) {
            CloseableHttpResponse re = graphAnalysisClient.uploadFile(fileName);
            log.info("Start Run Test: "+title);
            //获取响应内容
            String responseString = EntityUtils.toString(re.getEntity(), "UTF-8");
            log.info("Request URL：" + graphAnalysisClient.getUrl() + "，Request Parameter：" + params);
            log.info("Response：" + responseString);
            //创建JSON对象  把得到的响应字符串 序列化成json对象
            JSONObject responseJson = JSONObject.parseObject(responseString);
            String code = JsonHandleUtil.getValueByJpath(responseJson, "code");
            if("100".equals(code)){
                files.add(fileName);
            }
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
        for (String fileName:files
             ) {
            String param="{\"fileName\":\""+fileName+"\"}";
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
