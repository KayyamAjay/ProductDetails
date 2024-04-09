package com.products.productDetails.factory;

import com.products.productDetails.DTO.ProductDetailsDTO;
import com.products.productDetails.DTO.ResponseObject.ProductDetailsRO;
import com.products.productDetails.DTO.ResponseObject.ProductRO;
import com.products.productDetails.DTO.ResponseObject.StockDetailsRO;
import com.products.productDetails.constants.StockAvailable;
import com.products.productDetails.model.ProductDetails;


public class ProductDetailsFactory {
    public static ProductDetails mapFromProductDetailsDTO(ProductDetailsDTO productDetailsDTO){
        return ProductDetails.builder()
                .productName(productDetailsDTO.getProductName())
                .productBrand(productDetailsDTO.getProductBrand())
                .productPrice(productDetailsDTO.getProductPrice())
                .stockQuantity(productDetailsDTO.getStockQuantity())
                .build();
    }

    public static  ProductDetailsRO mapFromProductDetails(ProductDetails productDetails){
        return ProductDetailsRO.builder()
                .productId(productDetails.getProductId())
                .productBrand(productDetails.getProductBrand())
                .productName(productDetails.getProductName())
                .productPrice(productDetails.getProductPrice())
                .stockQuantity(productDetails.getStockQuantity())
                .build();
    }

    public static ProductRO mapFromProductDetailsDTO(ProductDetails productDetails){
        return ProductRO.builder()
                .productId(productDetails.getProductId())
                .productBrand(productDetails.getProductBrand())
                .productName(productDetails.getProductBrand())
                .productPrice(productDetails.getProductPrice())
                .isProductAvailable(checkProductAvailability(productDetails.getStockQuantity())).build();
    }

    private static StockAvailable checkProductAvailability(int stockQuantity) {
        if(stockQuantity>0){
            return StockAvailable.YES;
        }
        return StockAvailable.NO;
    }
    public static StockDetailsRO mapToStockDetails(ProductDetails productDetails){
        return StockDetailsRO.builder()
                .productId(productDetails.getProductId())
                .productName(productDetails.getProductName())
                .productQuantity(productDetails.getStockQuantity())
                .build();
    }
}
