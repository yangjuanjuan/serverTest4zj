package com.zjlab.qa.tests.graphAnalysis;

import com.alibaba.fastjson.JSONObject;
import com.zjlab.qa.apiClient.GraphAnalysisClientApi;
import com.zjlab.qa.utils.GetJsonValueUtil;
import com.zjlab.qa.utils.ReadExcelUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LoadDataTest {

    private GraphAnalysisClientApi graphAnalysisClient;
    private List<Map<String, String>> loadData;



    @BeforeClass
    public void setUp(){
        graphAnalysisClient=new GraphAnalysisClientApi();
        loadData = ReadExcelUtil.getExcuteList("loadData.xlsx");



    }
    //    通过读取Excel获取测试数据入参
    @DataProvider
    public Object[][] getLoadData(){
        Object[][] files = new Object[loadData.size()][];
        for(int i=0; i<loadData.size(); i++){
            files[i] = new Object[]{loadData.get(i)};
        }
        return files;
    }
    @Test(dataProvider = "getLoadData")
    public void loadData4Excel(Map<?,?> param) throws IOException {
        String title=(String) param.get("title");
        String params = (String) param.get("params");
        String expectCode = (String) param.get("expectCode");
        String expectMessage = (String) param.get("expectMessage");
        CloseableHttpResponse re = graphAnalysisClient.loadData(params);

        //获取响应内容
        String loadDataResStr = EntityUtils.toString(re.getEntity(), "UTF-8");
        System.out.println("接口请求"+graphAnalysisClient.getUrl()+"，入参："+params);
        System.out.println("接口返回："+loadDataResStr);
        //创建JSON对象  把得到的响应字符串 序列化成json对象
        JSONObject resJson = JSONObject.parseObject(loadDataResStr);
        String code = GetJsonValueUtil.getValueByJpath(resJson, "code");
        String message = GetJsonValueUtil.getValueByJpath(resJson, "message");
        Assert.assertEquals(code,expectCode,title+"; 实际的code："+code+"，期望返回的code："+expectCode);

        boolean isMessage=message.contains(expectMessage);
        Assert.assertTrue(message.contains(expectMessage),title+"; 实际的message："+message+"，期望返回的message："+expectMessage);


    }

    /**
     * 删除新建的标签页
     */
    @AfterClass
    public void tearDown(){


    }
}
