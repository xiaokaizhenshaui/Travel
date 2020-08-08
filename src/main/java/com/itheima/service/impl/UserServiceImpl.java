package com.itheima.service.impl;

import com.itheima.dao.UserDao;
import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.utils.Md5Utils;
import com.itheima.utils.MyBatisUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;

/**
 * service的实现类
 */
public class UserServiceImpl implements UserService {
    @Override
    public void register(User user) {
        //获得连接 并操作
        SqlSession sqlSession = MyBatisUtils.openSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        try {
            /**
             * 1.用户名不能为空
             * 2.校验用户名是否已经存在
             * 密码要加密 , 数据库保留的数据是密文数据(加密后的数据)
             * 3.在此处编写 逻辑代码
             */
            if(StringUtils.isBlank(user.getUsername())) throw new RuntimeException("USERNAME IS EMPTY .");
            int count = userDao.findExistsUserName(user.getUsername());
            if(count > 0) throw new RuntimeException("USERNAME IS EXISTS .");
            String md5Password = Md5Utils.encodeByMd5(user.getPassword());
            user.setPassword(md5Password);
            userDao.save(user);
        }catch (RuntimeException e){
            e.printStackTrace();//处理异常的地方  自定义异常
            throw  new RuntimeException(e);
        }catch (Exception e){
            e.printStackTrace();//处理异常的地方  未知异常
            throw  new RuntimeException("未知异常");
        }finally {
            MyBatisUtils.close(sqlSession);//释放资源
        }
    }

    /**
     * 校验用户名
     * @param username
     * @return
     */
    @Override
    public boolean checkName(String username) {
        //获得连接 并操作
        SqlSession sqlSession = MyBatisUtils.openSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        try {
            int count = userDao.findExistsUserName(username);
            if(count>0) return false;
            return true;
        }catch (Exception e){
            e.printStackTrace();//处理异常的地方
        }finally {
            MyBatisUtils.close(sqlSession);//释放资源
        }
        return false;
    }

    @Override
    public User pwdLogin(String username, String password) {
        //获得连接 并操作
        SqlSession sqlSession = MyBatisUtils.openSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        try {
            String passWord = Md5Utils.encodeByMd5(password);
            return userDao.pwdLogin(username,passWord);
        }catch (Exception e){
            e.printStackTrace();//处理异常的地方
        }finally {
            MyBatisUtils.close(sqlSession);//释放资源
        }
        return null;
    }

    /**
     * 模板方法
     * @param user
     */
    public void template(User user) {
        //获得连接 并操作
        SqlSession sqlSession = MyBatisUtils.openSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        try {

            //在此处编写 逻辑代码

        }catch (Exception e){
            e.printStackTrace();//处理异常的地方
        }finally {
            MyBatisUtils.close(sqlSession);//释放资源
        }
    }
}
