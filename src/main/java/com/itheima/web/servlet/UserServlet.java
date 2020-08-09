package com.itheima.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.domain.ResultInfo;
import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.utils.SmsUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static com.itheima.utils.BeanFactoryUtils.getBean;

/**
 * 处理增删改查所有的方法
 */
@WebServlet(name = "UserServlet" , urlPatterns = "/user")
@MultipartConfig
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
//        UserService userService = (UserService)getBean("userservice");
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
     * register code
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
//                UserService userService = (UserService)getBean("userservice");
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
     * 用户名密码登录
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
    protected void loginout(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        request.getSession().removeAttribute("loginUser");
        response.sendRedirect(request.getContextPath()+"/");
    }

    /**
     * telephone login
     * 1.获取浏览器数据
     * 2.处理数据
     * true --> 判断user是否存在
     * false -->
     * 3.响应结果
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void telLogin(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        ResultInfo resultInfo = null;
        String telephone = request.getParameter("telephone");
        String smsCode = request.getParameter("smsCode");
        String loginCode = (String) request.getSession().getAttribute("loginCode");
        if(StringUtils.equals(smsCode,loginCode)){
            UserService userService = (UserService)getBean("userservice");
            User user = userService.phoneLogin(telephone);
            if(user == null){
                resultInfo = new ResultInfo(false,"","Please register .");
            }else {
                resultInfo = new ResultInfo(true,"Login ...","");
                request.getSession().setAttribute("loginUser",user);
            }
        }else {
            resultInfo = new ResultInfo(false,"","SMSCODE WRONG .");
        }
        String json = new ObjectMapper().writeValueAsString(resultInfo);
        response.getWriter().print(json);
    }

    /**
     * 手机登录验证码
     * 1.获得phone号
     * 2.发送手机号
     * 3.将发送的验证码保存
     * 4.返回结果
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void loginSendSms(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        ResultInfo resultInfo;
        try {
//            String telephone = request.getParameter("telephone");
            String code = SmsUtil.createCode();//假设需要传入电话
            System.out.print(code);
            request.getSession().setAttribute("loginCode",code);
            resultInfo = new ResultInfo(true, "MESSAGE SEND SUCCESS .","");
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo = new ResultInfo(false, "","MESSAGE SEND FAIL.");
        }
        String json = new ObjectMapper().writeValueAsString(resultInfo);
        response.getWriter().print(json);
    }

    /**
     * 修改个人信息
     * 1.get data
     * 2.file update
     * 2.1 store into localhost
     * 2.2 get servlet localhost path
     * 2.3 change data
     * 3.redirect
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void UpdateInfo(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            User user = new User();
            BeanUtils.populate(user,parameterMap);
            Part pic = request.getPart("pic");
            if(pic != null && pic.getSize()>0){
                String fileName = pic.getSubmittedFileName();
                String realPath = this.getServletContext().getRealPath("/pic");
                InputStream is = pic.getInputStream();
                FileOutputStream os = new FileOutputStream(realPath + "/" + fileName);
                IOUtils.copy(is,os);
                is.close();
                os.close();
                user.setPic("pic/"+fileName);
            }
            UserService userService = (UserService)getBean("userservice");
            userService.updateUser(user);

            //update session
            User loginUser = userService.findById(user.getUid());
            request.getSession().setAttribute("loginUser",loginUser);

            response.sendRedirect(request.getContextPath() + "/home_index.jsp");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
