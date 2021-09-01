package com.zjlab.qa.tests.graphAnalysis;

import com.alibaba.fastjson.JSONArray;
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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListFileTest {
    private static final Logger log= LoggerFactory.getLogger(ListFileTest.class);
    private GraphAnalysisClientApi graphAnalysisClient;



    @BeforeClass
    public void setUp(){
        graphAnalysisClient=new GraphAnalysisClientApi();
    }
    @Test
    public void listFileTest() throws IOException {
            CloseableHttpResponse re = graphAnalysisClient.listFiles("{}");
            log.info("Start Run Test: 获取当前用户所有图数据文件");
            //获取响应内容
            String responseString = EntityUtils.toString(re.getEntity(), "UTF-8");
            log.info("Request URL：" + graphAnalysisClient.getUrl());
            log.info("Response：" + responseString);
            //创建JSON对象  把得到的响应字符串 序列化成json对象
            JSONObject responseJson = JSONObject.parseObject(responseString);
            String code = GetJsonValueUtil.getValueByJpath(responseJson, "code");
            String message = GetJsonValueUtil.getValueByJpath(responseJson, "message");
            String result=GetJsonValueUtil.getValueByJpath(responseJson, "result");

            Assert.assertEquals(code, "100", " 实际的code：" + code + "，期望返回的code：100" );
            Assert.assertTrue(message.contains("成功"), ";实际的message：" + message + "，期望返回的message：成功" );
            Assert.assertNotNull(result);
        }
    }





