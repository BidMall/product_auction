package com.example.product_auction.product.service;

import java.util.List;

import com.example.product_auction.product.dto.AuctionRequest;
import com.example.product_auction.product.dto.AuctionResponse;

public interface AuctionService {

	/**
	 * 단일 경매 상세 조회
	 * @param id
	 * @return
	 */
	AuctionResponse getAuctionById(Long id);

	/**
	 * 전체 경매 목록 (요약 정보 리스트)
	 * @return
	 */
	List<AuctionResponse> getAllAuction();

	/**
	 * 경매 생성
	 * @param auctionRequest
	 * @return
	 */
	AuctionResponse createAuction(AuctionRequest auctionRequest);

	/**
	 * 경매 수정
	 * @param auctionId
	 * @param auctionRequest
	 * @return
	 */
	AuctionResponse updateAuction(Long auctionId, AuctionRequest auctionRequest);

	/**
	 * 논리 삭제
	 * @param auctionId
	 */
	void deleteAuction(Long auctionId);

	/**
	 * 영구 삭제
	 * @param auctionId
	 */
	void deleteAuctionPermanently(Long auctionId);

	/**
	 * 종료 여부에 따른 경매 조회 (요약 정보 리스트)
	 * @param isClosed
	 * @return
	 */
	List<AuctionResponse> getAuctionsByClosedStatus(boolean isClosed);
}
