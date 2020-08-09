package com.itheima.service.impl;

import com.itheima.dao.AddressDao;
import com.itheima.dao.UserDao;
import com.itheima.domain.Address;
import com.itheima.domain.User;
import com.itheima.service.AddressService;
import com.itheima.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @author frankyang
 * @version 1.0
 * @creat 2020-08-07 4:16 PM
 * @work-email frankygang13280@gmail.com
 */
public class AddressServiceImpl implements AddressService {
    @Override
    public void saveAddressService(Address address) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        AddressDao addressDao = sqlSession.getMapper(AddressDao.class);
        try {
            addressDao.save(address);
        }catch (Exception e){
            e.printStackTrace();//处理异常的地方
        }finally {
            MyBatisUtils.close(sqlSession);//释放资源
        }
    }

    @Override
    public List<Address> findByUid(int uid) {
        //获得连接 并操作
        SqlSession sqlSession = MyBatisUtils.openSession();
        AddressDao addressDao = sqlSession.getMapper(AddressDao.class);
        try {
            return addressDao.findByUid(uid);
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
