package com.zjlab.qa.tests;

import com.alibaba.fastjson.JSONObject;
import com.zjlab.qa.base.TestBase;
import com.zjlab.qa.restClient.RestClient;
import com.zjlab.qa.utils.getJsonValueUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
public class GetTest01 extends TestBase {
    TestBase testBase;
    RestClient restClient;
    String host;
    String url;
    CloseableHttpResponse response;
    @BeforeClass
    public void setUp(){
        testBase = new TestBase();
        host = testBase.getValue("HOST");//读取config.properties里面的根url
        url = host + "/api/users?page=2"; //进入https://reqres.in/ 网站下拉 有Get/Post/Put方法测试的说明文档
    }
    @Test
    public void getListUsers() throws IOException {
        restClient = new RestClient();
        response = restClient.getApi(url);
        //获取响应内容
        String responseString = EntityUtils.toString(response.getEntity(),"UTF-8");
        //创建JSON对象  把得到的响应字符串 序列化成json对象
        JSONObject responseJson = JSONObject.parseObject(responseString);
        System.out.println("response json---->" + responseJson);
        //判断得到的结果是否正确
        //首先判断网站是否成功响应  先获取状态码
        int statusCode = response.getStatusLine().getStatusCode();
        //获取到的状态码和200 对比  如果相等就说明网站响应成功
        Assert.assertEquals(statusCode,200,"网页成功响应");
        //然后判断得到的相应内容是否和接口文档一致
        //比如 page=2  per_page=6 total=12是否正确
        //调用utils里面写好的 获取json value的方法
        String get_total = getJsonValueUtil.getValueByJpath(responseJson,"total");
        String get_first_name = getJsonValueUtil.getValueByJpath(responseJson,"data[0]/first_name");
        //断言研判接口返回值是否与预期一致
        Assert.assertEquals(get_total,"12","获取信息总数非12条");
        Assert.assertEquals(get_first_name,"Michael","用户名字不是Michael");

    }
    @Test
    public void Test01(){
        System.out.println("测试用例1");
    }
    @Test
    public void Test02(){
        System.out.println("测试用例2");
    }
    @Test
    public void Test03(){
        System.out.println("测试用例3");
    }
    @Test(timeOut = 1000)
    public void Test04() throws InterruptedException {
        System.out.println("测试用例4");
        Thread.sleep(1200);
    }

    }
