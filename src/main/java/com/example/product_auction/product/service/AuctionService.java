package com.example.product_auction.product.service;

import java.util.List;

import com.example.product_auction.product.domain.Auction;
import com.example.product_auction.product.dto.AuctionRequest;

public interface AuctionService {

	Auction getAuctionById(Long id);

	List<Auction> getAllAuction();

	Auction createAuction(AuctionRequest auctionRequest);

	Auction updateAuction(Long auctionId, AuctionRequest auctionRequest);

	void deleteAuction(Long auctionId);

	void deleteAuctionPermanently(Long auctionId);

	List<Auction> getAuctionsByClosedStatus(boolean isClosed);

}
