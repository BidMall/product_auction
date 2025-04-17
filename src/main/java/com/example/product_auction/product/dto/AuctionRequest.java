package com.example.product_auction.product.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuctionRequest {

	/** 상품 ID **/
	private Long productId;

	/** 경매 시작 시간 (옵션) **/
	private LocalDateTime startTime;

	/** 경매 종료 시간 (옵션) **/
	private LocalDateTime endTime;

}
