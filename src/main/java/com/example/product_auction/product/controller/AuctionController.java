package com.example.product_auction.product.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

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

	/**
	 * 경매 등록
	 * @param request 경매 등록 요청
	 * @return 등록된 경매 응답
	 */
	ResponseEntity<Auction.RegisterAuctionResponse> registerAuction(Auction.RegisterAuctionRequest request);

	/**
	 * 경매삭제
	 * @param request
	 * @return
	 */
	ResponseEntity<Auction.DeleteAuctionResponse> deleteAuction(
		@RequestBody Auction.DeleteAuctionRequest request);

}
