package com.itheima.service;

import com.itheima.domain.User;

/**
 * Service接口对象
 */
public interface UserService {
    //注册
    void register(User user);

    //校验用户名是否存在
    boolean checkName(String username);

    User pwdLogin(String username, String password);

    User phoneLogin(String telephone);

    void updateUser(User user);

    User findById(int uid);
}
