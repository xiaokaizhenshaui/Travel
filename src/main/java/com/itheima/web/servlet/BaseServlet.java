package com.itheima.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author frankyang
 * @version 1.0
 * @creat 2020-08-06 10:11 PM
 * @work-email frankygang13280@gmail.com
 */
public class BaseServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        //拿到本类的class
        //根据方法名获得method对象，获得private或者protected修饰的方法
        //暴力反射代码
        //执行代码
        try {
            Method method = this.getClass().getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            method.setAccessible(true);
            method.invoke(this,request,response);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("获取方法失败");
        }
        /*try {
            //方法名称
            String action = request.getParameter("action");

            //1.获得class对象 Class.forName()  类名.class  对象名.getClass()
            Class clazz = this.getClass();

            //2.通过action获得方法对象
            //参数1:"方法的名称"  参数2: 方法的真实参数类型.class
            Method method = clazz.getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);

            //3.暴力反射
            method.setAccessible(true);

            //4.执行代码
            //正常执行代码 this.save(request , response);
            //参数1 : 执行的方法对象是谁  参数2 : 执行方法时候传入的参数是谁
            method.invoke(this , request,response );

        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
