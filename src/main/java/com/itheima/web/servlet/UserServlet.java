package com.itheima.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.domain.ResultInfo;
import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.service.impl.UserServiceImpl;
import com.itheima.utils.BeanFactoryUtils;
import com.itheima.utils.SmsUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.spec.ECField;
import java.util.Map;

import static com.itheima.utils.BeanFactoryUtils.getBean;

/**
 * 处理增删改查所有的方法
 */
@WebServlet(name = "UserServlet" , urlPatterns = "/user")
public class UserServlet extends BaseServlet {
    /**
     * 判断用户名是否存在
     * 1.获得用户名的代码
     * 2.调用service 校验用户名
     * true  可以注册  false  不可以注册
     * 3.响应数据 json  对象转换json的代码
     * @param request
     * @param response
     */
    private void checkName(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        UserService userService = (UserService)getBean("userservice");
//        UserService userService = new UserServiceImpl();
        boolean flag = userService.checkName(username);
        ResultInfo resultInfo = null;
        if(flag){
            resultInfo = new ResultInfo(true, "LUCK DOG , YOUR INFO CAN USE .", "");
        }else {
            resultInfo = new ResultInfo(true,"","INFO CANNOT USE , YOU MUST CHANGE OTHORS .");
        }
        String json = new ObjectMapper().writeValueAsString(resultInfo);
        response.getWriter().print(json);
    }

    /**
     * 1.telephone
     * 2.code
     * 3.存入session
     * @param request
     * @param response
     * @throws IOException
     */
    private void sendRegisterCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResultInfo resultInfo;
        try {
//        String telephone = request.getParameter("telephone");
            String code = SmsUtil.createCode();
            System.out.println(code);
            request.getSession().setAttribute("registercode",code);
            resultInfo= new ResultInfo(true, "MESSAGE SEND SUCCESS .", "");
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo= new ResultInfo(false, "", "MESSAGE SEND FAIL .");
        }
        String json = new ObjectMapper().writeValueAsString(resultInfo);
        response.getWriter().print(json);
    }
    /**
     * 用户注册代码
     * //1.获得浏览器数据
     * //2.调用service保存数据
     * //3.响应页面结果
     * @param request
     * @param response
     */
    private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String smsCode = request.getParameter("smsCode");
        String registercode = (String) request.getSession().getAttribute("registercode");
        if( StringUtils.equals(smsCode,registercode)){
            try {
                Map<String, String[]> parameterMap = request.getParameterMap();
                User user = new User();
                BeanUtils.populate(user,parameterMap);

                UserService userService = (UserService)getBean("userservice");
//                UserService userService = new UserServiceImpl();
                userService.register(user);

                response.sendRedirect(request.getContextPath()+"/register_ok.jsp");
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("msg",e.getMessage());
                request.getRequestDispatcher("register.jsp").forward(request,response);
            }
        }else {
            request.setAttribute("msg","CHECK CODE AGAIN");
            request.getRequestDispatcher("register.jsp").forward(request,response);
        }

    }

    /**
     * 1.获取数据
     * 2.调用service执行登录
     * 3.返回结果 true | false
     * 4.转换成json
     * @param request
     * @param response
     */
    private void pwdLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserService userService = (UserService)getBean("userservice");
//        UserService userService = new UserServiceImpl();
        User user = userService.pwdLogin(username, password);
        ResultInfo resultInfo = null;
        if(user == null){
            resultInfo = new ResultInfo(false,"","USERNAME OR PASSWORD WRONG .");
        }else {
            request.getSession().setAttribute("loginUser",user);
            resultInfo = new ResultInfo(true,"LOGIN SUCCESS","");
        }
        String json = new ObjectMapper().writeValueAsString(resultInfo);
        response.getWriter().print(json);
    }

    /**
     *loginout
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void loginout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().removeAttribute("loginUser");
        response.sendRedirect(request.getContextPath()+"/");
    }

    /**
     * telephone login
     * 1.获取浏览器数据
     * 2.处理数据
     * 3.响应结果
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void telLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String telephone = request.getParameter("telephone");
        request.getParameter("");
    }

    /**
     * 收益登录验证码
     * 1.获得phone号
     * 2.发送手机号
     * 3.将发送的验证码保存
     * 4.返回结果
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void loginSendSms(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo resultInfo;
        String telephone = request.getParameter("telephone");
        String code = SmsUtil.createCode();
        request.getSession().setAttribute("loginCode",code);
        resultInfo = new ResultInfo(true, "MESSAGE SEND SUCCESS .","");

    }

    private void find(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("查");//*100
    }

    private void update(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("改");//*100
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("删");//*100
    }

    private void save(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("增"); //*100
    }
}
