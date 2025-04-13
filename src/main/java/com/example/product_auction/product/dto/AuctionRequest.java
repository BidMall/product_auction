package com.example.product_auction.product.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuctionRequest {

	/** 상품 ID **/
	private Long productId;

	/** 상품 이름 **/
	private String productName;

	/** 상품 설명 **/
	private String productDescription;

	/** 상품 가격 **/
	private Long startPrice;
}
