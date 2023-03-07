package com.example.stageperfectionementbackend.repository;

import com.example.stageperfectionementbackend.entities.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin("http://localhost:4200")
public interface ProduitRepository extends JpaRepository <Produit, Long> {
    List<Produit> findByName(String name);
    List<Produit> findByNameContains(String name);
    List<Produit> findByPriceBetween(double x, double y);
}
