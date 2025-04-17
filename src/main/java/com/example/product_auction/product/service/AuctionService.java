package com.example.product_auction.product.service;

import java.util.List;

import com.example.product_auction.product.domain.Auction;

public interface AuctionService {

	/**
	 * 진행중인 경매를 조회
	 * @return
	 */
	List<Auction.OngoingAuctionResponse> findByOnGoingAuctions();

	/**
	 * 모든 경매를 조회
	 * @return
	 */
	List<Auction.AuctionBaseResponse> findAllByAuctions();

	/**
	 * 종료된 경매를 조회
	 */
	List<Auction.ClosedAuctionResponse> findEndByAuctions();

	/**
	 * 경매 등록
	 * @param request
	 * @return
	 */
	Auction.RegisterAuctionResponse registerAuction(Auction.RegisterAuctionRequest request);
}
