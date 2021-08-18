package com.zjlab.qa.tests.graphAnalysis;

import com.alibaba.fastjson.JSONObject;
import com.zjlab.qa.apiClient.GraphAnalysisClientApi;
import com.zjlab.qa.apiClient.ProjectManage;
import com.zjlab.qa.common.ParseKeyword;
import com.zjlab.qa.utils.GetJsonValueUtil;
import com.zjlab.qa.utils.ReadExcelUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DeleteGraphByIdTest {
    private GraphAnalysisClientApi graphAnalysisClient;
    private List<Map<String, String>> deleteGraphData;
    private List<String> projectGraphIds;
    private ProjectManage projectManage;
    private String projId;
    private String graphId;


    @BeforeClass
    public void setUp(){
        projectManage=new ProjectManage();
        graphAnalysisClient=new GraphAnalysisClientApi();
        projectGraphIds=new ArrayList<String>();
        deleteGraphData = ReadExcelUtil.getExcuteList("deleteGraphById-test.xlsx");



    }
//    通过读取Excel获取测试数据入参
    @DataProvider
    public Object[][] deleteGraphData(){
        Object[][] files = new Object[deleteGraphData.size()][];
        for(int i=0; i<deleteGraphData.size(); i++){
            files[i] = new Object[]{deleteGraphData.get(i)};
        }
        return files;
    }
    @Test(dataProvider = "deleteGraphData")
    public void deleteGraphByIdTestByExcel(Map<?,?> param) throws IOException {
        String title=(String) param.get("title");
        String params = (String) param.get("params");
        String expectCode = (String) param.get("expectCode");
        String expectMessage = (String) param.get("expectMessage");
        List<String> placeholders = ParseKeyword.getKeywords(params);
//替换Excel中通过$占位的参数
        if(placeholders.size()>0 && placeholders.contains("projectId") && placeholders.contains("graphId")){
//            新建项目，获取项目id
            CloseableHttpResponse projRe= projectManage.create();
            String responseStr = EntityUtils.toString(projRe.getEntity(), "UTF-8");
            JSONObject responseJson = JSONObject.parseObject(responseStr);
            projId = GetJsonValueUtil.getValueByJpath(responseJson, "result");
            String addGraph="{\"projectId\":"+projId+"}";
//        新建标签页，获取标签页ID
            CloseableHttpResponse graphRe= graphAnalysisClient.addGraph(addGraph);
            String graphReStr = EntityUtils.toString(graphRe.getEntity(), "UTF-8");
            JSONObject graphReJson = JSONObject.parseObject(graphReStr);
            graphId = GetJsonValueUtil.getValueByJpath(graphReJson, "result/id");
            params="{\"projectId\":"+projId+",\"graphId\":"+graphId+"}";
        }

        CloseableHttpResponse re = graphAnalysisClient.deleteGraphById(params);

        //获取响应内容
        String delGraphString = EntityUtils.toString(re.getEntity(), "UTF-8");
        System.out.println("接口请求"+graphAnalysisClient.getUrl()+"，入参："+params);
        System.out.println("接口返回："+delGraphString);
        //创建JSON对象  把得到的响应字符串 序列化成json对象
        JSONObject delGraphJson = JSONObject.parseObject(delGraphString);
        String code = GetJsonValueUtil.getValueByJpath(delGraphJson, "code");
        String message = GetJsonValueUtil.getValueByJpath(delGraphJson, "message");

        Assert.assertEquals(code,expectCode,title+"; 实际的code："+code+"，期望返回的code："+expectCode);
        boolean isMessage=message.contains(expectMessage);
        Assert.assertTrue(message.contains(expectMessage),title+"; 实际的message："+message+"，期望返回的message："+expectMessage);


    }

    /**
     * 删除新建项目
     */
    @AfterClass
    public void tearDown(){
        String delPrams="{\"id\":"+projId+"}";
        CloseableHttpResponse re= projectManage.deleteById(delPrams);
        String responseString = null;
        try {
            responseString = EntityUtils.toString(re.getEntity(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("##############开始删除测试新增的项目##################");
        System.out.println("接口请求"+projectManage.getUrl()+"，入参："+delPrams);
        System.out.println("接口返回："+responseString);
        System.out.println("##############成功删除测试新增的项目##################");

        }

}



