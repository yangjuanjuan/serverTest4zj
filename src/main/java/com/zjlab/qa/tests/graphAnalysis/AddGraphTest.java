package com.zjlab.qa.tests.graphAnalysis;

import com.alibaba.fastjson.JSONObject;
import com.zjlab.qa.apiClient.GraphAnalysisClientApi;
import com.zjlab.qa.utils.GetJsonValueUtil;
import com.zjlab.qa.utils.ReadExcelUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddGraphTest {
    private GraphAnalysisClientApi graphAnalysisClient;
    private List<Map<String, String>> addGraphData;
    private List<String> projectGraphIds;


    @BeforeClass
    public void setUp(){
        graphAnalysisClient=new GraphAnalysisClientApi();
        projectGraphIds=new ArrayList<String>();
        addGraphData = ReadExcelUtil.getExcuteList("addGraph-test.xlsx");
        Reporter.log("test范文芳丰");


    }
//    通过读取Excel获取测试数据入参
    @DataProvider
    public Object[][] getAddGraphData(){
        Object[][] files = new Object[addGraphData.size()][];
        for(int i=0; i<addGraphData.size(); i++){
            files[i] = new Object[]{addGraphData.get(i)};
        }
        return files;
    }
    @Test(dataProvider = "getAddGraphData")
    public void addGraphTestByExcel(Map<?,?> param) throws IOException {
        String title=(String) param.get("title");
        String params = (String) param.get("params");
        String expectCode = (String) param.get("expectCode");
        String expectMessage = (String) param.get("expectMessage");
        CloseableHttpResponse re = graphAnalysisClient.addGraph(params);

        //获取响应内容
        String responseString = EntityUtils.toString(re.getEntity(), "UTF-8");
        System.out.println("接口请求"+graphAnalysisClient.getUrl()+"，入参："+params);
        System.out.println("接口返回："+responseString);
        //创建JSON对象  把得到的响应字符串 序列化成json对象
        JSONObject responseJson = JSONObject.parseObject(responseString);
        String code = GetJsonValueUtil.getValueByJpath(responseJson, "code");
        String message = GetJsonValueUtil.getValueByJpath(responseJson, "message");
        Assert.assertEquals(code,expectCode,title+"; 实际的code："+code+"，期望返回的code："+expectCode);
        if("100".equals(code)){
            Integer projectId= Integer.valueOf(GetJsonValueUtil.getValueByJpath(responseJson, "result/projectId"));
            Integer graphId= Integer.valueOf(GetJsonValueUtil.getValueByJpath(responseJson, "result/id"));
            String temp="{\"projectId\":"+projectId+",\"graphId\":"+graphId+"}";

            projectGraphIds.add(temp);
        }
        boolean isMessage=message.contains(expectMessage);
        Assert.assertTrue(message.contains(expectMessage),title+"; 实际的message："+message+"，期望返回的message："+expectMessage);


    }

    /**
     * 删除新建的标签页
     */
    @AfterClass
    public void tearDown(){
        for ( String param:projectGraphIds
             ) {
            CloseableHttpResponse re= graphAnalysisClient.deleteGraphById(param);
            String responseString = null;
            try {
                responseString = EntityUtils.toString(re.getEntity(), "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("##############开始删除测试新增的标签页##################");
            System.out.println("接口请求"+graphAnalysisClient.getUrl()+"，入参："+param);
            System.out.println("接口返回："+responseString);
            System.out.println("##############成功删除测试新增的标签页##################");

        }

    }

    }
