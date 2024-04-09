package com.products.productDetails.DTO.ResponseObject;

import com.products.productDetails.constants.StockAvailable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRO {
    private long productId;
    private String productName;

    private String productBrand;

    private long productPrice;

    private StockAvailable isProductAvailable;
}
