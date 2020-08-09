package com.itheima.dao;

import com.itheima.domain.Address;

import java.util.List;

/**
 * @author frankyang
 * @version 1.0
 * @creat 2020-08-09 11:02 AM
 * @work-email frankygang13280@gmail.com
 */
public interface AddressDao {
    void save(Address address);

    List<Address> findByUid(int uid);
}
