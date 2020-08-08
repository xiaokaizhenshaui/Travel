package com.itheima.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * ResultInfo  结果信息对象
 *  专门用来封装json数据返回
 *  一般来说有三个属性
 */
@Data
@AllArgsConstructor
public class ResultInfo {
    private boolean flag;//放回boolean
    private Object successMsg;//成功返回携带信息
    private String errorMsg;//失败返回提示信息

}
