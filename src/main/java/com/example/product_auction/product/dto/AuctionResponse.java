package com.example.product_auction.product.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@Getter
public class AuctionResponse {

	/** 경매 ID **/
	private Long id;

	/** 상품 ID **/
	private Long productId;

	/** 상품 이름 **/
	private String productName;

	/** 상품 설명 **/
	private String productDescription;

	/** 경매 시작 시간 **/
	private LocalDateTime startTime;

	/** 경매 종료 시간 **/
	private LocalDateTime endTime;

	/** 현재 최고 입찰가 **/
	private Long highestBid;

	/** 낙찰자 ID (User 서비스 참조) **/
	private Long winnerId;

	/** 경매 종료 여부 **/
	private Boolean isClosed;
}
