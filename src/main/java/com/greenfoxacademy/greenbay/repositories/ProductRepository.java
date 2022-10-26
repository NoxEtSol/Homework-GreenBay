package com.greenfoxacademy.greenbay.repositories;

import com.greenfoxacademy.greenbay.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
