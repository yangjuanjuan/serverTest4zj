package com.zjlab.qa.tests;

import com.alibaba.fastjson.JSONObject;
import com.zjlab.qa.base.TestBase;
import com.zjlab.qa.restClient.LoginClient;
import com.zjlab.qa.utils.ReadExcelUtil;
import com.zjlab.qa.utils.getJsonValueUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class LoginTest extends TestBase {


    String login_url;
    @BeforeClass
    public void setUp(){
        TestBase testBase = new TestBase();
        String host = testBase.getValue("HOST");//读取config.properties里面的根url
        String path = testBase.getValue("LOGIN_PATH");
        login_url = host + path;
    }
    @Test
    public void loginSuccess() throws IOException {


        CloseableHttpResponse re= LoginClient.login("","",login_url);
        //获取响应内容
        String responseString = EntityUtils.toString(re.getEntity(),"UTF-8");
        //创建JSON对象  把得到的响应字符串 序列化成json对象
        JSONObject responseJson = JSONObject.parseObject(responseString);
        System.out.println("response json---->" + responseJson);
    }
    @Test
    public void loginException() throws IOException {
       int a = 1/0;

    }

    @DataProvider
    public Object[][] getTestData(){
        List<Map<String, String>> result = ReadExcelUtil.getExcuteList("login-test.xlsx");
        Object[][] files = new Object[result.size()][];
        for(int i=0; i<result.size(); i++){
            files[i] = new Object[]{result.get(i)};
        }
        return files;
    }

    @Test(dataProvider = "getTestData")
    public void loginTestByExcel(Map<?,?> param) throws IOException {
        String name= (String) param.get("name");
        String password= (String) param.get("password");
        String  expectCode= (String) param.get("expect_code");
        String  expectMessage= (String) param.get("expect_message");
        CloseableHttpResponse re= LoginClient.login(name,password,login_url);

        //获取响应内容
        String responseString = EntityUtils.toString(re.getEntity(),"UTF-8");
        //创建JSON对象  把得到的响应字符串 序列化成json对象
        JSONObject responseJson = JSONObject.parseObject(responseString);
//        System.out.println("response json---->" + responseJson);
        String code = getJsonValueUtil.getValueByJpath(responseJson,"code");
        String message = getJsonValueUtil.getValueByJpath(responseJson,"message");
        Assert.assertEquals(code,expectCode,"实际的code："+code+"，期望返回的code："+expectCode);
        Assert.assertEquals(message,expectMessage,"实际的返回的："+message+"，期望的："+expectMessage);




//        String get_total = getJsonValueUtil.getValueByJpath(responseJson,"total");
//        String get_first_name = getJsonValueUtil.getValueByJpath(responseJson,"data[0]/first_name");
//        //断言研判接口返回值是否与预期一致
//        Assert.assertEquals(get_total,"12","获取信息总数非12条");
//        Assert.assertEquals(get_first_name,"Michael","用户名字不是Michael");



    }


}
