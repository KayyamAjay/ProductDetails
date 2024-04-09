package com.products.productDetails.DTO.ResponseObject;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockDetailsRO {

    public long productId;
    public String productName;
    public int productQuantity;
}
