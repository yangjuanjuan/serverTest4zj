package com.zjlab.qa.common;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ParseKeyword {
    private static final Logger logger= LoggerFactory.getLogger(ParseKeyword.class);
    public static List<String> getKeywords(String p){
        String reg = "(?<=(?<!\\\\)\\$\\{)(.*?)(?=(?<!\\\\)\\})";
        RegExp re = new RegExp();
        List<String> list = re.find(reg, p);
        return list;
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

    public static void main(String[] args) {
        logger.info("info");
    }
}