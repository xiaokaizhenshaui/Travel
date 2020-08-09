package com.itheima.service;

import com.itheima.domain.Address;
import com.itheima.domain.User;

import java.util.List;

/**
 * @author frankyang
 * @version 1.0
 * @creat 2020-08-07 4:16 PM
 * @work-email frankygang13280@gmail.com
 */
public interface AddressService {
    void saveAddressService(Address address);

    List<Address> findByUid(int loginUser);
}
