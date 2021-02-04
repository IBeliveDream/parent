package com.springboot.product.controller;

import com.springboot.product.entity.Product;
import com.springboot.product.service.ProductSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductSerivce productService;

    @GetMapping("test")
    public String test(@RequestParam("name") String name) {
        return "test：" + name;
    }

    @RequestMapping("post")
    public String post(@RequestBody Product product) {
        return "post：" + product.toString();
    }

    @RequestMapping("selectProduct")
    public String selectProduct(@RequestBody Product product) {
        Product product1 = productService.selectProduct(product);
        return "service返回参数：" + product1.toString();
    }
    @RequestMapping("update")
    public String update(@RequestBody Product product) {
        productService.update(product);
        return "更新成功";
    }
    @RequestMapping("insert")
    public String insert(@RequestBody Product product) {
        productService.insert(product);
        return "创建成功";
    }
}
