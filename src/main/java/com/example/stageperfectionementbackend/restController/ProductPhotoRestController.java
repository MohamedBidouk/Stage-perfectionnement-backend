package com.example.stageperfectionementbackend.restController;

import com.example.stageperfectionementbackend.entities.Product;
import com.example.stageperfectionementbackend.entities.ProductPhoto;
import com.example.stageperfectionementbackend.exception.ResourceNotFoundException;
import com.example.stageperfectionementbackend.message.ResponseFile;
import com.example.stageperfectionementbackend.message.ResponseMessage;
import com.example.stageperfectionementbackend.repository.ProductRepository;
import com.example.stageperfectionementbackend.service.ProductPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@CrossOrigin(origins = "*")
public class ProductPhotoRestController {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductPhotoService productPhotoService;

    @PostMapping("/{productId}/photos")
    public ResponseEntity<ResponseMessage> uploadPhoto(@PathVariable(value = "productId")Long productId,
                                                        @RequestBody MultipartFile photo){
        String message = "";
        try{
            Product product = productRepository.findById(productId).get();
            productPhotoService.store(photo, product);
            message = "Photo uploaded successfully";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + photo.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }
    @GetMapping("/{productId}/photos")
    public ResponseEntity<List<ResponseFile>> getAllFilesByProduct(@PathVariable("productId")Long productId){
        if(!productRepository.existsById(productId)){
            throw new ResourceNotFoundException("Product not found");
        }
        List<ResponseFile> photos = productPhotoService.getAllFilesByProduct(productId).map(productPhoto -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/photos/")
                    .path(productPhoto.getId())
                    .toUriString();

            return new ResponseFile(
                    productPhoto.getName(),
                    fileDownloadUri,
                    productPhoto.getType(),
                    productPhoto.getData().length
            );
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(photos);
    }
    @GetMapping("/photos/{id}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable String id){
        ProductPhoto productPhoto = productPhotoService.getFile(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + productPhoto.getName() + "\"")
                .body(productPhoto.getData());
    }
    @RequestMapping(value = "/{id}/photos", method = RequestMethod.DELETE)
    public void deleteProductPhotos(@PathVariable("id") Long productId){
        productPhotoService.deletePhotosByProductId(productId);
    }

    @DeleteMapping("/photos/{photoId}")
    public void deletePhoto(@PathVariable("photoId")String photoId){
        productPhotoService.deletePhotoById(photoId);
    }

}
