package com.itheima.utils;

import com.itheima.web.servlet.UserServlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author frankyang
 * @version 1.0
 * @creat 2020-08-07 3:37 PM
 * @work-email frankygang13280@gmail.com
 * BeanFactoryUtils : bean工厂对象
 */
public class BeanFactoryUtils {
    public static Object getBean(String key){
        Object o = null;
        try {
            InputStream is = BeanFactoryUtils.class.getClassLoader().getResourceAsStream("beans.properties");
            Properties properties = new Properties();
            properties.load(is);
            String className = properties.getProperty(key);
            o = Class.forName(className).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return o;
    }
}
