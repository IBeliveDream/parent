package com.springboot.product.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.springboot.product.entity.Product;
import com.springboot.product.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductDao {

    @Autowired
    private ProductMapper productMapper;

    public Product selectProduct(Product product) {
        return productMapper.selectProduct(product);
    }

    public List<Product> query(Product product) {
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Product::getId, product.getId());
        return productMapper.selectList(wrapper);
    }

    public Integer queryByDate(String date) {
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Product::getId, date);
        Integer model = productMapper.selectCount(wrapper);
        return model;
    }

    public Integer update(Product product) {
        Integer model = productMapper.updateById(product);
        return model;
    }

    public Integer insert(Product product) {
        Integer model = productMapper.insert(product);
        return model;
    }
}
