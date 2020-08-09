package com.itheima.web.servlet;

import com.itheima.domain.Address;
import com.itheima.domain.User;
import com.itheima.service.AddressService;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.itheima.utils.BeanFactoryUtils.getBean;

/**
 * @author frankyang
 * @version 1.0
 * @creat 2020-08-09 8:38 AM
 * @work-email frankygang13280@gmail.com
 */
@WebServlet("/address")
public class AddressServlet extends HttpServlet {
    /**
     * 添加数据
     * 1.获得数据
     * 1.1 获得map
     * 1.2 创建地址
     * 1.3 封装数据
     * 1.4 确定其他数据
     * 1.5 获得登陆数据
     * 2. 处理数据
     * 3. 响应数据
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void addAddress(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            Address address = new Address();
            BeanUtils.populate(address,parameterMap);
            address.setIsDefault("0");
            User loginUser = (User) request.getSession().getAttribute("loginUser");
            address.setUser(loginUser);
            AddressService addressservice = (AddressService) getBean("addressservice");
            addressservice.saveAddressService(address);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据uid查询用户
     * 1.获得数据
     * 2.处理数据
     * 3.响应数据
     * 4.存域
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void findByUid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        AddressService addressservice = (AddressService) getBean("addressservice");
        List<Address> addressList = addressservice.findByUid(loginUser.getUid());
        request.setAttribute("addressList",addressList);
        request.getRequestDispatcher("home_address.jsp").forward(request,response);
    }
}
