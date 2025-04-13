package com.example.product_auction.product.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

/**
 * 실시간 입찰금액 조회용
 */
@Data
@Builder
public class BidStatusResponse {

	/** 조회하는 경매 ID **/
	private Long auctionId;

	/** 현재까지 최고 입찰 금액 **/
	private Long highestBid;

	/** 현재최고 입찰자 **/
	private Long highestBidderId;

	/** 총 입찰자 수 **/
	private Integer bidCount;

	/** 경매 종료 여부 **/
	private Boolean isClosed;

	/** 경매 종료 시간 **/
	private LocalDateTime endTime;
}
