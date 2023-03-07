package com.example.stageperfectionementbackend.service;

import com.example.stageperfectionementbackend.entities.Product;
import com.example.stageperfectionementbackend.entities.ProductPhoto;
import com.example.stageperfectionementbackend.repository.ProductPhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@Service
public class ProductPhotoService {
    @Autowired
    ProductPhotoRepository productPhotoRepository;
   /* public ProductPhoto store(MultipartFile file, Product product) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        ProductPhoto ProductPhoto = new ProductPhoto(fileName, file.getContentType(), file.getBytes());
        ProductPhoto.setProduct(product);

        return productPhotoRepository.save(ProductPhoto);
    }
    public ProductPhoto getPhoto(String id){
        return productPhotoRepository.findById(id).get();
    }
    public Stream<ProductPhoto> getAllPhoto(){
        return productPhotoRepository.findAll().stream();
    }
    public Stream<ProductPhoto> getAllPhotosByProductId(Long productId){
        return productPhotoRepository.findByProductId(productId).stream();
    }*/
   public ProductPhoto store(MultipartFile file, Product product) throws IOException {
       String fileName = StringUtils.cleanPath(file.getOriginalFilename());
       ProductPhoto ProductPhoto = new ProductPhoto(fileName, file.getContentType(), file.getBytes());
       ProductPhoto.setProduct(product);

       return productPhotoRepository.save(ProductPhoto);
   }

    /*public ProductPhoto storeAll(MultipartFile[] files) throws IOException {
        for (MultipartFile file : files){
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            ProductPhoto ProductPhoto = new ProductPhoto(fileName, file.getContentType(), file.getBytes());
        }
        return productPhotoRepository.saveAll(f)
    }*/

    public ProductPhoto getFile(String id) {
        return productPhotoRepository.findById(id).get();
    }

    public Stream<ProductPhoto> getAllFiles() {
        return productPhotoRepository.findAll().stream();
    }

    public Stream<ProductPhoto> getAllFilesByProduct(Long productId){
        return productPhotoRepository.findByProductId(productId).stream();
    }
    public void deletePhotosByProductId(Long productId){
        productPhotoRepository.deleteByProductId(productId);
    }
    public void deletePhotoById(String id){
        productPhotoRepository.deleteById(id);
    }
}
