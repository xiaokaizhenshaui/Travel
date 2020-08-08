package com.itheima.web;

import com.itheima.dao.UserDao;
import com.itheima.domain.User;
import com.itheima.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class TestDemo {
    public static void main(String[] args) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        List<User> userList = userDao.findAll();
        for (User user : userList) {
            System.out.println(user);
        }
        MyBatisUtils.close(sqlSession);
    }
}
