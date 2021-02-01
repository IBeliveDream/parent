package com.springboot.product.service;

import com.springboot.product.dao.ProductDao;
import com.springboot.product.entity.Product;
import com.springboot.product.utile.DateUtil;
import com.springboot.product.utile.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//@Slf4j
@Service
public class ProductSerivce {

    @Autowired
    ProductDao productDao;

    public Product selectProduct(Product product) {

        return productDao.selectProduct(product);
    }

    public Boolean update(Product product) {
        productDao.update(product);
        return true;
    }

    public Boolean insert(Product product) {

        String date = DateUtil.nowDateStr();

        String num = "0";
        int newNum = productDao.queryByDate(date);
        if (newNum > 0) {
            num = String.valueOf(newNum);
        }
        // yyHHmm + 顺番6位
        String id = date + getSequence(num);

        product.setId(Long.valueOf(id));
        productDao.insert(product);
        return true;
    }

    /**
     * 得到6位的序列号,长度不足6位,前面补0
     *
     * @param str
     * @return
     */
    public static String getSequence(String str) {
        if (StringUtil.isEmpty(str)) {
            str = "0";
        }
        int len = str.length();
        if (len >= 6) {
            return str;
        }
        int rest = 6 - len;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rest; i++) {
            sb.append('0');
        }
        sb.append(str);
        return sb.toString();
    }


}
