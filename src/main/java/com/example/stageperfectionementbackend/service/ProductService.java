package com.example.stageperfectionementbackend.service;

import com.example.stageperfectionementbackend.entities.Product;

import java.util.List;

public interface ProductService {
    Product saveProduct(Product product);
    Product updateProduct(Long id, Product product);
    void deleteProduct(Product product);
    void deleteProductById(Long id);
    Product getProduct(Long id);
    List<Product> getAllProduct();
    List<Product> findByName(String name);
    List<Product> findByNameContains(String name);
    List<Product> findByPriceBetween(double x, double y);
    
}
