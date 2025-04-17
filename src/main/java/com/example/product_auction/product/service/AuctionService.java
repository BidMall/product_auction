package com.example.product_auction.product.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.example.product_auction.product.domain.Auction;

public interface AuctionService {

	/**
	 * 진행중인 경매를 조회
	 * @return
	 */
	List<Auction.OngoingAuctionResponse> getOngoingAuctions();

	/**
	 * 모든 경매를 조회
	 * @return
	 */
	List<Auction.AuctionBaseResponse> findAllByAuctions();

	/**
	 * 종료된 경매를 조회
	 */
	List<Auction.ClosedAuctionResponse> getClosedAuctions();

	/**
	 * 경매 등
	 * @param request
	 * @return
	 */
	@Transactional
	Auction.RegisterAuctionResponse registerAuction(Auction.RegisterAuctionRequest request);
}
