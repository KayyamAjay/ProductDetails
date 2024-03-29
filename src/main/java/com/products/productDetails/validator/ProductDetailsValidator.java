package com.products.productDetails.validator;

import com.products.productDetails.model.ProductDetails;
import com.products.productDetails.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductDetailsValidator {

    private final ProductRepository productRepository;

    public Boolean isProductExist(long productId){
        Optional<ProductDetails> productDetails =  productRepository.findById(productId);
        if(productDetails.isPresent()){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
