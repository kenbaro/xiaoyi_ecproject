package com.yeeshop.dao;

import java.util.List;

import com.yeeshop.entity.Product;

public interface IProductDAO {
    List<Product> findAll();
    List<Product> findSpecialProducts();
    Product findById(Integer id);
    List<Product> findHotSaleProduct();
    List<Product> findByCategoryId(Integer id);
    List<Product> findLastestProducts();
    List<Product> findTopViewProducts();
    List<Product> findOnsaleProducts();
    List<Product> findbyIds(String ids);
    List<Product> findSpecialPrice();
    List<Product> findByKeywords(String keywords);
    void update(Product product);
    Product create(Product entity);
    Product delete(Integer id);
    
}
