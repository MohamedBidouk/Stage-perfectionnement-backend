package com.example.stageperfectionementbackend.service;

import com.example.stageperfectionementbackend.entities.Produit;

import java.util.List;

public interface ProduitService {
    Produit saveProduit(Produit produit);
    Produit updateProduit(Long id, Produit produit);
    void deleteProduit(Produit produit);
    void deleteProduitById(Long id);
    Produit getProduit(Long id);
    List<Produit> getAllProduit();
    List<Produit> findByName(String name);
    List<Produit> findByNameContains(String name);
    List<Produit> findByPriceBetween(double x, double y);
}
