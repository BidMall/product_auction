package com.example.product_auction.product.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Bid {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** 경매 ID (Auction FK) **/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "auction_id", referencedColumnName = "id", nullable = false)
	private Auction auction;

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

	// ================= DTO =================

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class BidListRequest {
		/** 조회하려는 경매 ID **/
		private Long auctionId;
	}

	/**
	 * 새로운 입찰 반환
	 */
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class BidResponse {
		private Long auctionId;
		private Long bidderId;
		private Long bidAmount;
		private LocalDateTime bidTime;
	}
}
