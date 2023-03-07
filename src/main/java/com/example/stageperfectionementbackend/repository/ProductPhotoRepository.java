package com.example.stageperfectionementbackend.repository;

import com.example.stageperfectionementbackend.entities.ProductPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.transaction.Transactional;
import java.util.List;
@CrossOrigin("http://localhost:4200")
public interface ProductPhotoRepository extends JpaRepository <ProductPhoto, String> {
    List<ProductPhoto> findByProductId(Long productId);
    @Transactional
    void deleteByProductId(Long productId);

}
