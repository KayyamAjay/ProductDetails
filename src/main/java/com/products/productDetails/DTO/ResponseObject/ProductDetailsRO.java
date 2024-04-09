package com.products.productDetails.DTO.ResponseObject;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDetailsRO {
    private long productId;
    private String productName;

    private String productBrand;

    private long productPrice;

    private int stockQuantity;
}
