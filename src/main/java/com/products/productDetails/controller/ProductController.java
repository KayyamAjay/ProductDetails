package com.products.productDetails.controller;

import com.products.productDetails.DTO.ProductDetailsDTO;
import com.products.productDetails.DTO.ResponseObject.ProductDetailsRO;
import com.products.productDetails.DTO.ResponseObject.ProductRO;
import com.products.productDetails.DTO.ResponseObject.StockDetailsRO;
import com.products.productDetails.exceptions.ProductNotFoundException;
import com.products.productDetails.factory.ProductDetailsFactory;
import com.products.productDetails.model.ProductDetails;
import com.products.productDetails.service.ProductService;
import com.products.productDetails.validator.ProductDetailsValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    private final ProductDetailsValidator productDetailsValidator;
    private final Logger logger =
            LoggerFactory.getLogger(ProductController.class);


    @Operation(summary = "Create a Product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a product ",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDetailsRO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid Data, Please Check Input Parameters and Value",
                    content = @Content)
    })
    @PostMapping("/products")
    public ResponseEntity<ProductDetailsRO> createProduct
            (@Valid  @RequestBody ProductDetailsDTO productDetailsDTO){
        logger.info("Inside createProduct Method of ProductController Class");
        ProductDetails productDetails = ProductDetailsFactory.mapFromProductDetailsDTO(productDetailsDTO);
        ProductDetailsRO createdProduct = ProductDetailsFactory.mapFromProductDetails(
               productService.createProduct(productDetails));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @Operation(summary = "Get all Product Details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all products details ",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductRO.class)) })
    })
    @GetMapping("/products")
    public ResponseEntity<List<ProductRO>> retrieveAllProducts(){
        logger.info("Inside retrieveAllProducts Method of ProductController Class");
        List<ProductDetails> productDetails = productService.retrieveAllProducts();
        List<ProductRO> productRO = productDetails.stream()
                .map(details->ProductDetailsFactory.mapFromProductDetailsDTO(details))
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(productRO);
    }

    @Operation(summary = "Get Product Details based on productId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved products details by given id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductRO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid ProductId",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No Product Found",
                    content = @Content)

    })
    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductRO> retrieveProductDetailsById(@PathVariable("productId") long productId)
            throws ProductNotFoundException {
        logger.info("Inside retrieveProductDetailsById Method of ProductController Class");
        ProductRO productRO = ProductDetailsFactory.mapFromProductDetailsDTO(
                productService.retrieveProductDetailsById(productId));
        return ResponseEntity.status(HttpStatus.OK).body(productRO);
    }

    @Operation(summary = "Update Product based on productId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a product ",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDetailsRO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid Data, Please Check Input Parameters and Value",
                    content = @Content),
            @ApiResponse(responseCode = "204", description = "Successfully updated the product details",
                    content = @Content)

    })
    @PutMapping("/products/{productId}")
    public ResponseEntity<ProductDetailsRO> updateProductDetails
            (@PathVariable("productId") long productId,@Valid @RequestBody ProductDetailsDTO productDetailsDTO){
        logger.info("Inside updateProductDetails Method of ProductController Class");
        ProductDetails productDetails = ProductDetailsFactory.mapFromProductDetailsDTO(productDetailsDTO);
        Boolean isProductAvailable = productDetailsValidator.isProductExist(productId);
        if(!isProductAvailable){
            logger.info("There is no Product available with the given Id, Creating a new product");
            ProductDetailsRO createdProduct = ProductDetailsFactory.mapFromProductDetails(
                    productService.createProduct(productDetails));
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        }
        productService.updateProductDetails(productId,productDetails);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Delete Product based on productId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the product details",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No Products available to Delete!",
                    content = @Content)


    })
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Void> removeProduct(@PathVariable("productId") long productId) throws ProductNotFoundException {
        logger.info("Inside removeProduct Method of ProductController Class");
        Boolean isProductAvailable = productDetailsValidator.isProductExist(productId);
        if(!isProductAvailable){
            throw new ProductNotFoundException("No Products available to Delete!");
        }
        productService.removeProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Get Stock Details based on productId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved stock details by given id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StockDetailsRO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid ProductId",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No Product Found",
                    content = @Content)

    })
    @GetMapping("/stock-details/{productId}")
    public ResponseEntity<StockDetailsRO> getStockInformation(@PathVariable("productId") long productId) throws ProductNotFoundException {
        logger.info("Inside getStockInformation of ProductController Class");

        ProductDetails productDetailsRO = productService.retrieveProductDetailsById(productId);

        StockDetailsRO stockDetailsRO = ProductDetailsFactory.mapToStockDetails(productDetailsRO);

        return ResponseEntity.status(HttpStatus.OK).body(stockDetailsRO);
    }

}