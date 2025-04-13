package com.example.product_auction.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.product_auction.product.domain.Bid;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {

	public List<Bid> findByAuctionId(Long auctionId);

}
