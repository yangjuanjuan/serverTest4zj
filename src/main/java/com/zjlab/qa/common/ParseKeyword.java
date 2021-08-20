package com.zjlab.qa.common;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseKeyword {
    private static final Logger logger= LoggerFactory.getLogger(ParseKeyword.class);
    public static List<String> getKeywords(String p){
        String reg = "(?<=(?<!\\\\)\\$\\{)(.*?)(?=(?<!\\\\)\\})";
        RegExp re = new RegExp();
        List<String> list = re.find(reg, p);
        return list;
    }
    /**
     * 替换String中的${xxx}字符串
     */
    public static String replacePeso(String src, Map<String,String> replacements) {
        // 正则匹配 ${xx}
        Pattern regex = Pattern.compile("\\$\\{(.*?)\\}");
        for (String key : replacements.keySet()) {
            Matcher matcher = regex.matcher(src);
            boolean flag = matcher.find();
            if (!flag) {
                // 没有匹配到，结束
                break;
            }
            String k = matcher.group(0);// 取得匹配到的 ${xxx}
            if (replacements.containsKey(key)) {
                src = src.replaceAll("\\$\\{" + key + "\\}", replacements.get(key));
            }

        }
        return src;
    }

//    public static void main(String[] args) {
//
//
//        System.out.println(ParseKeyword.getKeywords("{\"projectId\":${projectId},\"graphId\":${graphId}}"));
//        System.out.println(p.getKeywords("a${a}a"));
//        System.out.println(p.getKeywords("a\\${a}a"));
//        System.out.println(p.getKeywords("a${a\\}a"));
//        System.out.println(p.getKeywords("a${a\\}a}a"));
//        System.out.println(p.getKeywords("a${a}a${"));
//        System.out.println(p.getKeywords("a${ab}a${a}"));
//
//
//    }


}