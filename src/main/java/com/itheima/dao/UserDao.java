package com.itheima.dao;

import com.itheima.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    //查询所有
    List<User> findAll();

    //保存user对象
    void save(User user);

    //判断用户名是否存在
    int findExistsUserName(String username);

    User pwdLogin(@Param("username") String username, @Param("password") String passWord);

    User telLogin(String telephone);
}
