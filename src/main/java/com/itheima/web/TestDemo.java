package com.itheima.web;

import com.itheima.dao.UserDao;
import com.itheima.domain.User;
import com.itheima.utils.MyBatisUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class TestDemo {
    public static void main(String[] args) {
        String s = null;
        System.out.printf(String.valueOf(StringUtils.isEmpty(s)));
    }
}
