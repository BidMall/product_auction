package com.example.product_auction.product.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**  상품 이름 **/
	private String name;

	/**  상품 설명 **/
	@Column(length = 1000)
	private String description;

	/**  상품 이미지 **/
	private String imageUrl;

	/** 상품 시작가 **/
	private Long startPrice;

	/** 상품 상태 (진행 중, 판매 완료 등) **/
	@Enumerated(EnumType.STRING)
	private ProductStatus status;

	/** 판매자 ID **/
	/**
	 * User 서비스 연동
	 */
	private Long sellerId;

	/** 상품 생성 시간 **/
	private LocalDateTime createdAt;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
		if (this.status == null) {
			this.status = ProductStatus.WAITING;
		}
	}
}
