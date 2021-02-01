package com.springboot.product.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

public class ProductInfo {

    @TableId(value = "id",type = IdType.INPUT)
    private Long id;
    private Integer productId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "ProductInfo{" +
                "id=" + id +
                ", productId=" + productId +
                '}';
    }
}
