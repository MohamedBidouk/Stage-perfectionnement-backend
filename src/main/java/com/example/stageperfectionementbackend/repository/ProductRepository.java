package com.example.stageperfectionementbackend.repository;

import com.example.stageperfectionementbackend.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RepositoryRestResource(path = "rest")
@CrossOrigin("http://localhost:4200")
public interface ProductRepository extends JpaRepository <Product, Long> {
    List<Product> findByName(String name);
    List<Product> findByNameContains(String name);
    List<Product> findByPriceBetween(double x, double y);
}
