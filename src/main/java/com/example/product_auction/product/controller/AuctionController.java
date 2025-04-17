package com.example.product_auction.product.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.product_auction.product.domain.Auction;

public interface AuctionController {

	/**
	 * @return 전체 경매 목록
	 */
	ResponseEntity<List<Auction.AuctionBaseResponse>> getAllAuctions();

	/**
	 * @return 진행중 경매 목록
	 */
	ResponseEntity<List<Auction.OngoingAuctionResponse>> getOnGoingAuctions();

	/**
	 * @return 종료 경매 목록
	 */
	ResponseEntity<List<Auction.ClosedAuctionResponse>> getClosedAuctions();

}
