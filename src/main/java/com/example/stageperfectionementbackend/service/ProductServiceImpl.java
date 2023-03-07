package com.example.stageperfectionementbackend.service;

import com.example.stageperfectionementbackend.entities.Product;
import com.example.stageperfectionementbackend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    ProductRepository productRepository;


    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product newProduct) {
        Product product = getProduct(id);
        if(newProduct.getName()!= null)
            product.setName(newProduct.getName());
        if(newProduct.getDescription()!= null)
            product.setDescription(newProduct.getDescription());
        if(newProduct.getPrice() >=0)
            product.setPrice(newProduct.getPrice());
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).get();
    }

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> findByNameContains(String name) {
        return productRepository.findByNameContains(name);
    }

    @Override
    public List<Product> findByPriceBetween(double x, double y) {
        return productRepository.findByPriceBetween(x,y);
    }
}
