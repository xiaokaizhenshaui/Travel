package com.itheima.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 地址实体类
 */
@Data  //提供get/set toString方法
@AllArgsConstructor //提供全参构造
@NoArgsConstructor//提供无参构造
public class Address implements Serializable {

    private Integer aid;

    private String contact;

    private String address;

    private String telephone;

    private String isDefault;

    private User user;

}
