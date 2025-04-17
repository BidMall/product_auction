package com.example.product_auction.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
가import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.product_auction.product.domain.Auction;
import com.example.product_auction.product.domain.Bid;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
	List<Bid> findByAuctionId(Long request);

	@Query("SELECT b FROM Bid b WHERE b.auction.id = :auctionId AND b.auction.status = :status")
	List<Bid> findByAuctionIdAndAuctionStatus(@Param("auctionId") Long auctionId,
		@Param("status") Auction.AuctionStatus status);

}
