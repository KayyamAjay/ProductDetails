package com.products.productDetails.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDetailsDTO {
    @NotEmpty(message = "ProductName should not be empty or null")
    private String productName;

    @NotEmpty(message = "ProductBrand should not be empty or null")
    private String productBrand;

    @Min(value = 1,message = "ProductPrice cannot be 0")
    private int productPrice;

    @PositiveOrZero(message = "StockQuantity cannot be less than 0")
    private int stockQuantity;
}
