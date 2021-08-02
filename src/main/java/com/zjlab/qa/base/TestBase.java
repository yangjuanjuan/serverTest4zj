package com.zjlab.qa.base;


import java.util.Locale;
import java.util.ResourceBundle;

public class TestBase {
    //新建测试基类 此类为所有测试类的父类
    private ResourceBundle rb ;
    public TestBase(){
            rb= ResourceBundle.getBundle("com/zjlab/qa/config/config", Locale.getDefault());
    }
    public String getValue(String key){
        return rb.getString(key);
    }


}
/**

public class TestBase {
    //新建测试基类 此类为所有测试类的父类
    public Properties pro;
    //读取配置文件  把读取配置文件的操作卸载构造方法中  我也不知道为什么(摊手 可能这样 效率比较高
    public TestBase()  {
        pro = new Properties();
        try {
            FileInputStream fis = new FileInputStream(System.getProperty("user.dir")//获取当前工程目录
                    + "/src/main/com/qa/config/config.properties");//获取config.properties文件目录
            pro.load(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
**/