package com.greenfoxacademy.greenbay.repositories;

import com.greenfoxacademy.greenbay.models.entities.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {

}
