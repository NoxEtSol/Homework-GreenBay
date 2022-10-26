package com.greenfoxacademy.greenbay.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.greenfoxacademy.greenbay.models.DTOs.ResponseDto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product  implements ResponseDto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String description;
  private String photoUrl;
  private String seller;
  private int startingPrice;
  private int lastBid;
  private int purchasePrice;

  @OneToOne
  private Customer buyer;

  @JsonIgnore
  @OneToMany
  private List<Bid> bids;

  public Product(
      String name,
      String description,
      String photoUrl,
      String seller,
      int startingPrice,
      int purchasePrice) {
    this.name = name;
    this.description = description;
    this.photoUrl = photoUrl;
    this.seller = seller;
    this.startingPrice = startingPrice;
    this.lastBid = 0;
    this.purchasePrice = purchasePrice;
    this.bids = new ArrayList<>();
  }

  public Product() {
  }
}
