package com.example.product_auction.product.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
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
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Auction {

	/** 경매 ID **/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** 상품ID **/
	private Long productId;

	/** 경매 시작 시간 **/
	private LocalDateTime startTime;

	/** 경매 종료 시간 **/
	private LocalDateTime endTime;

	/** 상품 설명 **/
	private String description; // 상품 설명 추가

	/** 최고 입찰가 **/
	private Long highestBid;
	private Long winnerId;

	/** 경매 종료 여부 **/
	private Boolean isClosed;

	/** 경매 삭제 여부 **/
	private Boolean isDeleted;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@PrePersist
	protected void onCreate() {
		this.highestBid = 0L;
		this.isClosed = false;
		this.isDeleted = false;
	}

	public void deleteAuction() {
		this.isDeleted = true;
	}

	// ===== DTO 시작 =====

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
		private Boolean isDeleted;
	}

	/**
	 * 진행중인 경매 조회용 DTO
	 */
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@SuperBuilder
	public static class OngoingAuctionResponse extends AuctionBaseResponse {
		private String productName;
		private Boolean isClosed;
	}

	/**
	 * 완료된 경매 조회용 DTO
	 */
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@SuperBuilder
	public static class ClosedAuctionResponse extends AuctionBaseResponse {
		private String productName;
		private Boolean isClosed;
		private Long winnerId;
	}

	/**
	 * 경매 등록 request, response 만들어야함
	 * 경매 등록은 어떤 Request?
	 * 그리고 등록되면 어떤 Response?
	 */
	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class RegisterAuctionRequest {
		private Long productId;
		private String description;
		private LocalDateTime startTime;
		private LocalDateTime endTime;
	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class RegisterAuctionResponse {
		private Long auctionId;
		private Long productId;
		private String description;
		private LocalDateTime startTime;
		private LocalDateTime endTime;
		private String message;
	}
}
