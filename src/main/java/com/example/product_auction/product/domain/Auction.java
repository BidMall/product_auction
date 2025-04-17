package com.example.product_auction.product.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Auction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long productId;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private Long highestBid;
	private Long winnerId;
	private Boolean isClosed;
	private Boolean isDeleted;

	@PrePersist
	protected void onCreate() {
		this.highestBid = 0L;
		this.isClosed = false;
		this.isDeleted = false;
	}

	public void deleteAuction() {
		this.isDeleted = true;
	}

	// Request DTO (경매 생성 요청)
	@Getter
	@Builder
	public static class AuctionRequest {
		private Long productId;
		private LocalDateTime startTime;
		private LocalDateTime endTime;
	}

	// Response DTO (경매 조회 응답)
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class AuctionResponse {
		private Long id;
		private Long productId;
		private LocalDateTime startTime;
		private LocalDateTime endTime;
		private Long highestBid;
		private Long winnerId;
		private Boolean isClosed;
		private Boolean isDeleted;
	}

	// Simple Response DTO (경매 처리 결과 응답)
	@Getter
	@AllArgsConstructor
	@Builder
	public static class SimpleAuctionResponse {
		private String message;
		private int returnCode;
	}

	// Update Request DTO (경매 상태 변경 요청)
	@Getter
	@Builder
	public static class AuctionUpdateRequest {
		private Long id;
		private Long highestBid;
		private Long winnerId;
		private Boolean isClosed;
	}

	// Auction Info Response DTO (경매 목록 조회 응답)
	@Getter
	@AllArgsConstructor
	@Builder
	public static class AuctionInfoResponse {
		private Long id;
		private Long productId;
		private Long highestBid;
		private Boolean isClosed;
	}
}
