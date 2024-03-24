package com.products.productDetails.factory;

import com.products.productDetails.DTO.ProductDetailsDTO;
import com.products.productDetails.DTO.ResponseObject.ProductDetailsRO;
import com.products.productDetails.model.ProductDetails;
import org.springframework.stereotype.Component;

@Component
public class ProductDetailsFactory {
    public ProductDetails mapFromProductDetailsDTO(ProductDetailsDTO productDetailsDTO){
        return ProductDetails.builder()
                .productName(productDetailsDTO.getProductName())
                .productBrand(productDetailsDTO.getProductBrand())
                .productPrice(productDetailsDTO.getProductPrice())
                .stockQuantity(productDetailsDTO.getStockQuantity())
                .build();
    }

    public ProductDetailsRO mapFromProductDetails(ProductDetails productDetails){
        return ProductDetailsRO.builder()
                .productId(productDetails.getProductId())
                .productBrand(productDetails.getProductBrand())
                .productName(productDetails.getProductName())
                .productPrice(productDetails.getProductPrice())
                .stockQuantity(productDetails.getStockQuantity())
                .build();
    }
}
