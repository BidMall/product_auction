package com.example.product_auction.product.domain;

import lombok.Getter;

@Getter
public enum ProductStatus {
	WAITING("대기 중"),
	AUCTIONING("진행 중"),
	SOLD("낙찰 완료"),
	CANCELED("취소됨");

	private final String description;

	ProductStatus(String description) {
		this.description = description;
	}
}
