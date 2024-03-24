package com.products.productDetails.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "PRODUCT_DETAILS")
public class ProductDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="PRODUCT_ID")
    private Long productId;
    @Column(name="PRODUCT_NAME")
    private String productName;

    @Column(name="PRODUCT_BRAND")
    private String productBrand;

    @Column(name="PRODUCT_PRICE")
    private int productPrice;
    @Column(name="PRODUCT_QUANTITY")
    private int stockQuantity;
}
