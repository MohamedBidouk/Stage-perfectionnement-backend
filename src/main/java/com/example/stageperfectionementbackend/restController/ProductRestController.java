package com.example.stageperfectionementbackend.restController;

import com.example.stageperfectionementbackend.entities.Product;
import com.example.stageperfectionementbackend.message.Response;
import com.example.stageperfectionementbackend.message.ResponseFile;
import com.example.stageperfectionementbackend.service.ProductPhotoService;
import com.example.stageperfectionementbackend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ProductRestController {
    @Autowired
    ProductService productService;
    @Autowired
    ProductPhotoService productPhotoService;
    @RequestMapping(path = "all", method = RequestMethod.GET)
    public List<Product> getAllProduct(){
        return productService.getAllProduct();
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Product getProductById(@PathVariable("id") Long id){
        return productService.getProduct(id);
    }
    @RequestMapping(value="/q={name}",method = RequestMethod.GET)
    public Product getProductByName(@PathVariable("name") String name) {
        return productService.findByName(name).get(0);
    }
    @RequestMapping(value = "/PName/{name}", method = RequestMethod.GET)
    public List<Product> findProductByNameContains(@PathVariable("name") String name){
        return productService.findByNameContains(name);
    }
    @RequestMapping(method = RequestMethod.POST)
    public Product createProduct(@RequestBody Product product){
        return productService.saveProduct(product);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Product updateProduct(@PathVariable("id") Long id, @RequestBody Product product){
        return productService.updateProduct(id, product);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteProduct(@PathVariable("id") Long id){
        productService.deleteProductById(id);
    }
    @RequestMapping(path = "hello", method = RequestMethod.GET)
    public String hello(){
        return "hello";
    }

    @GetMapping("/allProducts")
    public ResponseEntity<List<Response>> getAllProducts(){
        List<Product> products = productService.getAllProduct();
        List<Response> responseList = new ArrayList<>();

        int i = 0;

        while(i < products.size()){
            List<ResponseFile> photos = productPhotoService.getAllFilesByProduct(products.get(i).getId()).map(productPhoto -> {
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
            List<String> productPhotosUrls = new ArrayList<>();
            for(ResponseFile photo : photos){
                productPhotosUrls.add(photo.getUrl());
            }

            Response response = new Response(products.get(i).getId(), products.get(i).getName(), productPhotosUrls,
                    products.get(i).getDescription(), products.get(i).getPrice());
            responseList.add(response);
            i++;
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }

    @GetMapping("/{productId}/detail")
    public ResponseEntity<Response> getProductDetail(@PathVariable("productId")Long productId){
        Product product = productService.getProduct(productId);
        List<ResponseFile> photos = productPhotoService.getAllFilesByProduct(product.getId()).map(productPhoto -> {
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
        List<String> productPhotosUrls = new ArrayList<>();
        for(ResponseFile photo : photos){
            productPhotosUrls.add(photo.getUrl());
        }
            Response response = new Response(product.getId(), product.getName(), productPhotosUrls,
                    product.getDescription(), product.getPrice());



        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
