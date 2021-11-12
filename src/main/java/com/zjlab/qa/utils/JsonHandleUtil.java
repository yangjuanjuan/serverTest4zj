package com.zjlab.qa.utils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
public class JsonHandleUtil {
    //Json解析
    public static String getValueByJpath(JSONObject responseJson, String jpath) {

        Object obj = responseJson;
        for(String s : jpath.split("/")) {
            if(!s.isEmpty()) {
                if(!(s.contains("[") || s.contains("]"))) {
                    obj = ((JSONObject) obj).get(s);
                }else if(s.contains("[") || s.contains("]")) {
                    obj =((JSONArray)((JSONObject)obj).get(s.split("\\[")[0])).get(Integer.parseInt(s.split("\\[")[1].replaceAll("]", "")));
                }
            }
        }
        return obj.toString();
    }
   public static boolean strCompare(String actual, String expected, String jpath ){
        String act = getValueByJpath(str2Json(actual),jpath);
        String exp = getValueByJpath(str2Json(expected),jpath);
        return act.contains(exp);
   }

   public static JSONObject str2Json(String str){
        return  JSONObject.parseObject(str);
   }
}

