package com.greenfoxacademy.greenbay.services;

import com.greenfoxacademy.greenbay.models.DTOs.AllSellableDto;
import com.greenfoxacademy.greenbay.models.DTOs.NewProductDto;
import com.greenfoxacademy.greenbay.models.DTOs.ResponseDto;
import com.greenfoxacademy.greenbay.models.DTOs.SellableDto;
import com.greenfoxacademy.greenbay.models.DTOs.SpecificNotSellableDto;
import com.greenfoxacademy.greenbay.models.DTOs.SpecificSellableDto;
import com.greenfoxacademy.greenbay.models.entities.Customer;
import com.greenfoxacademy.greenbay.models.entities.Product;
import java.util.List;

public interface ProductService {

  boolean productHasValidPhotoUrl(NewProductDto dto);

  Product saveNewProduct(NewProductDto dto, String username);

  SpecificSellableDto convertToSpecificSellableDto(Product product);

  SpecificNotSellableDto convertToSpecificNotSellableDto(Product product);

  SellableDto convertToSellableDto(Product product);

  AllSellableDto getAllSellableProductsAsDto(List<SellableDto> sellableDtos);

  List<Product> getOnlySellableProducts();

  List<Product> getOnlySellableProducts(int n);

  List<SellableDto> convertAllSellableProductsToDto(List<Product> sellables);

  Product getProductById(Long id);

  boolean hasBuyer(Product product);

  void save(Product product);

  boolean canPlaceBid(Customer customer, int amount, Product product);

  ResponseDto placeBidOrBuy(Customer customer, int bid, Product product);
}
