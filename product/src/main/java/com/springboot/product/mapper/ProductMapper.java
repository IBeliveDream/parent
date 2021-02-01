package com.springboot.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.product.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ProductMapper extends BaseMapper<Product> {

    Product selectProduct(Product product);
}
