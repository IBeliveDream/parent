package com.springboot.product.entity;

import java.time.LocalDateTime;

/**
 * com.springboot.product.entity
 *
 * @author Jin
 * @Date 18:52 2021/2/5
 */
public class order {
    Integer id;
    String orderName;
    String orderNo;
    LocalDateTime beginTime;
    LocalDateTime endTime;
    Boolean isDelete;
    String creatBy;
    LocalDateTime creatTime;
    String updateBy;
    LocalDateTime updateTime;
}
