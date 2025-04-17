package com.example.product_auction.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.product_auction.product.domain.Auction;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {

	@Query("SELECT a FROM Auction a JOIN FETCH a.product WHERE a.status = :status")
	List<Auction> findByStatusWithProduct(@Param("status") Auction.AuctionStatus status);

}
