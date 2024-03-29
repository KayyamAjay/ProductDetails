package com.products.productDetails.service;

import com.products.productDetails.error.ProductNotFoundException;
import com.products.productDetails.model.ProductDetails;
import com.products.productDetails.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final Logger logger = LoggerFactory.getLogger(ProductService.class);
    public ProductDetails createProduct(ProductDetails productDetails) {
        return productRepository.save(productDetails);
    }

    public List<ProductDetails> retrieveAllProducts() {
        return productRepository.findAll();
    }

    public ProductDetails retrieveProductDetailsById(long productId) throws ProductNotFoundException {
        Optional<ProductDetails> productDetails =  productRepository.findById(productId);
        if(productDetails.isEmpty()){
            logger.info("Unable to find product details based on given Id");
            throw new ProductNotFoundException("Product Details Not Found");
        }
        return productDetails.get();
    }

    public void updateProductDetails(long productId, ProductDetails productDetails) {
        ProductDetails updatedProductDetails = ProductDetails.builder()
                    .productId(productId)
                    .productName(productDetails.getProductName())
                    .productBrand(productDetails.getProductBrand())
                    .stockQuantity(productDetails.getStockQuantity())
                    .productPrice(productDetails.getProductPrice())
                    .build();

        productRepository.save(updatedProductDetails);
    }

    public void removeProduct(long productId) {
        productRepository.deleteById(productId);
    }
}
