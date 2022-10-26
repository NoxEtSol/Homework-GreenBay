package com.greenfoxacademy.greenbay.services;

import com.greenfoxacademy.greenbay.exceptions.BidTooLowException;
import com.greenfoxacademy.greenbay.exceptions.InsufficientFundsException;
import com.greenfoxacademy.greenbay.exceptions.NoSuchProductException;
import com.greenfoxacademy.greenbay.exceptions.ProductAlreadyBoughtException;
import com.greenfoxacademy.greenbay.models.DTOs.AllSellableDto;
import com.greenfoxacademy.greenbay.models.DTOs.NewProductDto;
import com.greenfoxacademy.greenbay.models.DTOs.ResponseDto;
import com.greenfoxacademy.greenbay.models.DTOs.SellableDto;
import com.greenfoxacademy.greenbay.models.DTOs.SpecificNotSellableDto;
import com.greenfoxacademy.greenbay.models.DTOs.SpecificSellableDto;
import com.greenfoxacademy.greenbay.models.entities.Bid;
import com.greenfoxacademy.greenbay.models.entities.Customer;
import com.greenfoxacademy.greenbay.models.entities.Product;
import com.greenfoxacademy.greenbay.repositories.BidRepository;
import com.greenfoxacademy.greenbay.repositories.CustomerRepository;
import com.greenfoxacademy.greenbay.repositories.ProductRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.UrlValidator;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final ModelMapper modelMapper;
  private final BidRepository bidRepository;
  private final CustomerRepository customerRepository;

  @Override
  public boolean productHasValidPhotoUrl(NewProductDto dto) {
    UrlValidator validator = new UrlValidator();
    return validator.isValid(dto.getPhotoUrl());
  }

  @Override
  public Product saveNewProduct(NewProductDto dto, String username) {
    return productRepository.save(
        new Product(
            dto.getName(),
            dto.getDescription(),
            dto.getPhotoUrl(),
            username,
            dto.getStartingPrice(),
            dto.getPurchasePrice()));
  }

  @Override
  public SpecificSellableDto convertToSpecificSellableDto(Product product) {
    return modelMapper.map(product, SpecificSellableDto.class);
  }

  @Override
  public SpecificNotSellableDto convertToSpecificNotSellableDto(Product product) {
    return modelMapper.map(product, SpecificNotSellableDto.class);
  }

  @Override
  public SellableDto convertToSellableDto(Product product) {
    return modelMapper.map(product, SellableDto.class);
  }

  @Override
  public AllSellableDto getAllSellableProductsAsDto(List<SellableDto> sellableDtos) {
    return new AllSellableDto(sellableDtos);
  }

  @Override
  public List<Product> getOnlySellableProducts() {
    return productRepository.findAll().stream()
        .filter(item -> item.getBuyer() == null)
        .limit(20)
        .collect(Collectors.toList());
  }

  @Override
  public List<Product> getOnlySellableProducts(int n) {
    return productRepository.findAll().stream().filter(product -> product.getBuyer() == null)
        .limit(20).collect(
            Collectors.toList());
  }

  @Override
  public List<SellableDto> convertAllSellableProductsToDto(List<Product> sellables) {
    return sellables.stream().map(this::convertToSellableDto).collect(Collectors.toList());
  }

  @Override
  public Product getProductById(Long id) {
    Optional<Product> product = productRepository.findById(id);
    if (!product.isPresent()) {
      throw new NoSuchProductException("Product service: getSellableDtoById");
    }
    return product.get();
  }

  @Override
  public boolean hasBuyer(Product product) {
    return product.getBuyer() != null;
  }

  @Override
  public void save(Product product) {
    productRepository.save(product);
  }

  @Override
  public boolean canPlaceBid(Customer customer, int amount, Product product) {
    if (hasBuyer(product)) {
      throw new ProductAlreadyBoughtException("Product service: canPlaceBid()");
    }
    if (customer.getBalance() < amount) {
      throw new InsufficientFundsException("Product service: canPlaceBid()");
    }
    if (amount <= product.getLastBid() || amount <= product.getStartingPrice()) {
      throw new BidTooLowException("Product service: canPlaceBid()");
    }
    return true;
  }

  @Override
  public ResponseDto placeBidOrBuy(Customer customer, int bid, Product product) {
    if (bid < product.getPurchasePrice()) {
      Bid newBid = new Bid(product, customer, bid);
      newBid.setCustomer(customer);
      newBid.setProduct(product);
      bidRepository.save(newBid);
      product.getBids().add(newBid);
      product.setLastBid(bid);
      productRepository.save(product);
      customer.setBalance(customer.getBalance() - bid);
      customerRepository.save(customer);
      return convertToSpecificSellableDto(product);
    } else {
      product.getBids()
          .forEach(
              previousBid -> {
                Customer bidder = previousBid.getCustomer();
                bidder.setBalance(bidder.getBalance() + previousBid.getAmount());
                customerRepository.save(bidder);
              });
      product.setBuyer(customer);
      productRepository.save(product);
      customer.getOwnedProducts().add(product);
      customer.setBalance(customer.getBalance() - product.getPurchasePrice());
      customerRepository.save(customer);
      return convertToSpecificNotSellableDto(product);
    }
  }
}
