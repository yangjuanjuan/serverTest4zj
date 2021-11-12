package com.zjlab.qa.clientApi;

import com.alibaba.fastjson.JSONObject;
import com.zjlab.qa.base.ApiBaseClient;
import com.zjlab.qa.utils.JsonHandleUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.cookie.Cookie;

import java.io.IOException;
import java.util.Map;

public class DataSetClientApi extends ApiBaseClient {
    private static String UPLOAD_FILES = "UPLOAD_FILES";
    private static String FILE_WRITE = "FILE_WRITE";
    private static String DELETE = "DELETE";
    private LoginClientApi loginClientApi;
    private Cookie cookie;

    public DataSetClientApi() {
        loginClientApi = new LoginClientApi();
        loginClientApi.setLoginCookie();
        cookie = loginClientApi.getLoginCookie();

    }

    /**
     * 图文件上传 uploadFiles
     * @param
     * @return
     */
    public CloseableHttpResponse uploadFiles(String req) {
        JSONObject params= JSONObject.parseObject(req);
        String fileName=params.getString("file");
        String file= GraphAnalysisClientApi.class.getResource("/fileData/").getPath()+fileName;
        params.remove("file");
        Map<String, Object> others=(Map<String,Object>)params;
        CloseableHttpResponse response= this.uploadFile(file,others,UPLOAD_FILES,cookie);
        return  response;
    }

    public CloseableHttpResponse fileWrite(String req) {

        CloseableHttpResponse response = this.run(req, FILE_WRITE, cookie);
        return response;
    }

    public CloseableHttpResponse delete(String req) {
        CloseableHttpResponse response = this.run(req, DELETE, cookie);
        return response;
    }

    /**
     * 上传文件到默认分类下，返回上传的数据id
     * @param req
     * @return
     */
    public String uploadFileToDefaultCategory(String req){
        CloseableHttpResponse uploadRes=this.uploadFiles(req);
        JSONObject uploadJson =this.convertResponseJson(uploadRes);
        String file= JsonHandleUtil.getValueByJpath(uploadJson, "result[0]/fileName");
        String name= "AutoTest"+"_"+ RandomStringUtils.randomAlphanumeric(3);;
        String importType=file.split("-")[0].toLowerCase();
        String sheetName = JsonHandleUtil.getValueByJpath(uploadJson,"result[0]/name");
        String data= JsonHandleUtil.getValueByJpath(uploadJson,"result[0]/head");
        String set="\"separate\":\"\",\"quote\":\"\",\"escape\":\"\"";
        if(importType.contains("csv")) {
            set = "\"separate\":\",\",\"quote\":\"\\\"\",\"escape\":\"\\\"\"";
        }
        String writeParams="{\"categoryId\":983,\"importType\":\""+importType+
                "\",\"name\":\""+name+"\",\"files\":[{\"filename\":\""+file+
                "\",\"firstLineAsFields\":true,\"data\":"+data+"," + "\"sheetName\":\""+sheetName+"\"}],\"charSet\":\"utf-8\","+set+",\"firstImport\":true}";
        CloseableHttpResponse writeRes=this.fileWrite(writeParams);
        JSONObject writeJson =this.convertResponseJson(writeRes);
        String code = JsonHandleUtil.getValueByJpath(writeJson, "code");
        if(code.equals(100)){
            return JsonHandleUtil.getValueByJpath(writeJson, "result/datasetId");
        }
        return "";




    }

    public static void main(String[] args) throws IOException {
        DataSetClientApi test = new DataSetClientApi();
        String req = "{\"file\":\"graphShowData.xlsx\",\"charSet\":\"utf-8\",\"firstLineAsFields\":\"true\"}";
//        CloseableHttpResponse re=test.uploadFiles(req);
//        //获取响应内容
//        String responseString = EntityUtils.toString(re.getEntity(),"UTF-8");
//        JSONObject responseJson = JSONObject.parseObject(responseString);
//        System.out.println(responseString);
          String id =test.uploadFileToDefaultCategory(req);







    }







}
