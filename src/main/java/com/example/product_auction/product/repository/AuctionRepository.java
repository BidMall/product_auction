package com.example.product_auction.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.product_auction.product.domain.Auction;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {

	List<Auction> findByIsClosed(Boolean isClosed);

	List<Auction> findByProductId(Long productId);
}
