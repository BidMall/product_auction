package com.example.product_auction.product.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Bid {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** 경매 ID (Auction FK) **/
	private Long auctionId;

	/** 입찰자 ID (User 서비스 연동) **/
	private Long bidderId;

	/** 입찰 금액 **/
	private Long bidAmount;

	/** 입찰 시간 **/
	private LocalDateTime bidTime;

	@PrePersist
	protected void onCreate() {
		this.bidTime = LocalDateTime.now();
	}
}
