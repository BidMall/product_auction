package com.example.product_auction.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.product_auction.product.domain.Auction;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {

	// 진행 중 및 종료된 경매 확인
	List<Auction> findByStatus(Auction.AuctionStatus status);
}
