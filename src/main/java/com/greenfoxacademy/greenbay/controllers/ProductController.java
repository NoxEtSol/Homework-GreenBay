package com.greenfoxacademy.greenbay.controllers;

import com.greenfoxacademy.greenbay.exceptions.InvalidInputException;
import com.greenfoxacademy.greenbay.exceptions.InvalidUrlException;
import com.greenfoxacademy.greenbay.models.DTOs.NewProductDto;
import com.greenfoxacademy.greenbay.models.DTOs.ResponseDto;
import com.greenfoxacademy.greenbay.models.entities.Customer;
import com.greenfoxacademy.greenbay.models.entities.Product;
import com.greenfoxacademy.greenbay.services.CustomerService;
import com.greenfoxacademy.greenbay.services.ProductService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

  private final ProductService productService;
  private final CustomerService customerService;

  @PostMapping("/add")
  public ResponseEntity<ResponseDto> addNewProduct(
      @RequestHeader(name = "Authorization") String bearerToken,
      @RequestBody @Valid NewProductDto dto) {
    Customer customer = customerService.getCustomerFromAuthorizationHeader(bearerToken);
    if (!productService.productHasValidPhotoUrl(dto)) {
      throw new InvalidUrlException("/api/v1/items/add");
    }
    return ok(productService.saveNewProduct(dto, customer.getUsername()));
  }

  @GetMapping("/sellable")
  public ResponseEntity<ResponseDto> showAllSellableProducts() {
    return ok(
        productService.getAllSellableProductsAsDto(
            productService.convertAllSellableProductsToDto(productService.getOnlySellableProducts())));
  }

  @GetMapping("/sellable/{page}")
  public ResponseEntity<ResponseDto> showAllSellableProducts(@PathVariable Integer page) {
    return ok(
        productService.getAllSellableProductsAsDto(
            productService.convertAllSellableProductsToDto(productService.getOnlySellableProducts(page))));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponseDto> getProductById(@PathVariable Long id) {
    if (id < 1) {
      throw new InvalidInputException("/api/v1/products/" + id);
    }
    Product product = productService.getProductById(id);
    if (productService.hasBuyer(product)) {
      return ok(productService.convertToSpecificNotSellableDto(product));
    }
    return ok(productService.convertToSpecificSellableDto(product));
  }

  @PostMapping("/{id}/{bid}")
  public ResponseEntity<ResponseDto> placeBidOnProduct(
      @RequestHeader(name = "Authorization") String bearerToken,
      @PathVariable(name = "id") Long id,
      @PathVariable(name = "bid") Integer bid) {
    if (id < 1) {
      throw new InvalidInputException("/api/v1/products/" + id);
    }
    Customer customer = customerService.getCustomerFromAuthorizationHeader(bearerToken);
    Product product = productService.getProductById(id);
    if (productService.canPlaceBid(customer, bid, product)) {
      return ok(productService.placeBidOrBuy(customer, bid, product));
    }
    return badRequest().build();
  }
}