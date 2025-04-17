package com.example.product_auction.product.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Auction {

	// ===== 경매 상태 enum =====
	public enum AuctionStatus {
		ONGOING,   // 경매 진행 중
		CLOSED,     // 경매 종료
		PENDING,
		CANCELED
	}

	/** 경매 ID **/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** 경매 시작 시간 **/
	private LocalDateTime startTime;

	/** 경매 종료 시간 **/
	private LocalDateTime endTime;

	/** 상품 설명 **/
	private String description;

	/** 최고 입찰가 **/
	private Long highestBid;

	/** 낙찰자 ID **/
	private Long winnerId;

	/** 경매 상태 **/
	@Enumerated(EnumType.STRING)
	private AuctionStatus status;

	/** 상품 연관관계 **/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	// ===== DTO 정의 =====

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@SuperBuilder
	public static class AuctionBaseResponse {
		private Long id;
		private Long productId;
		private LocalDateTime startTime;
		private LocalDateTime endTime;
		private Long highestBid;
		private AuctionStatus status;
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@SuperBuilder
	public static class OngoingAuctionResponse extends AuctionBaseResponse {
		private String productName;
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@SuperBuilder
	public static class ClosedAuctionResponse extends AuctionBaseResponse {
		private String productName;
		private Long winnerId;
	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class RegisterAuctionRequest {
		private Product product;
		private String description;
		private LocalDateTime startTime;
		private LocalDateTime endTime;
	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	@ToString
	public static class RegisterAuctionResponse {
		private Long auctionId;
		private Product product;
		private String description;
		private LocalDateTime startTime;
		private LocalDateTime endTime;
		private AuctionStatus status;

	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class DeleteAuctionRequest {
		private Long auctionId;
	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class DeleteAuctionResponse {
		private Long auctionId;
	}
}
