package com.zjlab.qa.tests.graph;

import com.zjlab.qa.clientApi.GraphBuildClientApi;
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
import java.util.List;
import java.util.Map;

public class RedoTest {
    private static final Logger log= LoggerFactory.getLogger(RedoTest.class);
    private GraphBuildClientApi graphBuildClientApi;
    private List<Map<String, String>> caseParams;

    /**
     * 前期先直接在自动化测试项目中构建好一个图构建的结果
     */
    @BeforeClass
    public void setUp(){
        graphBuildClientApi=new GraphBuildClientApi();
        caseParams = ReadExcelUtil.getExcelList("graphBuildCase.xlsx",GraphBuildClientApi.GRAPH_REDO);

    }

    //    通过读取Excel获取测试数据Request Parameter
    @DataProvider
    public Object[][] caseParams(){
        Object[][] files = new Object[caseParams.size()][];
        for(int i=0; i<caseParams.size(); i++){
            files[i] = new Object[]{caseParams.get(i)};
        }
        return files;
    }
    @Test(dataProvider = "caseParams")
    public void redo(Map<?,?> param) throws IOException, InterruptedException {
        String title = (String) param.get("title");
        String params = (String) param.get("params");
        String expectResult = (String) param.get("expectResult");
        String isRun = (String) param.get("isRun");
        if (isRun.equals("1")) {
            List<String> placeholders = ParseKeyword.getKeywords(params);
            CloseableHttpResponse re = graphBuildClientApi.redo(params);

            log.info("Start Run Test: "+title);
            log.info("Request URL：" + graphBuildClientApi.getUrl() + "，Request Parameter：" + params);
            //获取响应内容
            String res = EntityUtils.toString(re.getEntity(), "UTF-8");
            log.info("Response：" + res);

            Assert.assertTrue(JsonHandleUtil.strCompare(res,expectResult,"code"));
            Assert.assertTrue(JsonHandleUtil.strCompare(res,expectResult,"message"));

        }

    }

}
