package com.products.productDetails.controller;

import com.products.productDetails.DTO.ProductDetailsDTO;
import com.products.productDetails.DTO.ResponseObject.ProductDetailsRO;
import com.products.productDetails.error.ProductNotFoundException;
import com.products.productDetails.factory.ProductDetailsFactory;
import com.products.productDetails.model.ProductDetails;
import com.products.productDetails.service.ProductService;
import com.products.productDetails.validator.ProductDetailsValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/product-details/v1")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    private final ProductDetailsFactory productDetailsFactory;

    private final ProductDetailsValidator productDetailsValidator;
    private final Logger logger =
            LoggerFactory.getLogger(ProductController.class);
    @PostMapping("/product")
    public ResponseEntity<ProductDetailsRO> createProduct
            (@Valid  @RequestBody ProductDetailsDTO productDetailsDTO){
        logger.info("Inside createProduct Method of ProductController Class");
        ProductDetails productDetails = productDetailsFactory.mapFromProductDetailsDTO(productDetailsDTO);
        ProductDetailsRO createdProduct = productDetailsFactory.mapFromProductDetails(
               productService.createProduct(productDetails));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDetailsRO>> retrieveAllProducts(){
        logger.info("Inside retrieveAllProducts Method of ProductController Class");
        List<ProductDetails> productDetails = productService.retrieveAllProducts();
        List<ProductDetailsRO> productDetailsRO = productDetails.stream()
                .map(details->productDetailsFactory.mapFromProductDetails(details))
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(productDetailsRO);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDetailsRO> retrieveProductDetailsById(@PathVariable("id") long productId)
            throws ProductNotFoundException {
        logger.info("Inside retrieveProductDetailsById Method of ProductController Class");
        ProductDetailsRO productDetailsRO = productDetailsFactory.mapFromProductDetails(
                productService.retrieveProductDetailsById(productId));
        return ResponseEntity.status(HttpStatus.OK).body(productDetailsRO);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductDetailsRO> updateProductDetails
            (@PathVariable("id") long productId,@Valid @RequestBody ProductDetailsDTO productDetailsDTO){
        logger.info("Inside updateProductDetails Method of ProductController Class");
        ProductDetails productDetails = productDetailsFactory.mapFromProductDetailsDTO(productDetailsDTO);
        Boolean isProductAvailable = productDetailsValidator.isProductExist(productId);
        if(!isProductAvailable){
            logger.info("There is no Product available with the given Id, Creating a new product");
            ProductDetailsRO createdProduct = productDetailsFactory.mapFromProductDetails(
                    productService.createProduct(productDetails));
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        }
        productService.updateProductDetails(productId,productDetails);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> removeProduct(@PathVariable("id") long productId){
        logger.info("Inside removeProduct Method of ProductController Class");
        productService.removeProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
