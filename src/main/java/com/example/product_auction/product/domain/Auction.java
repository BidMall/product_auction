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

	/** 상품 ID (FK) **/
	private Long productId;

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

	/** 생성 시 기본값 설정 **/

	/** 삭제 여부 **/
	private Boolean isDeleted;

	@PrePersist
	protected void onCreate() {
		this.highestBid = 0L;
		this.isClosed = false;
		this.isDeleted = false;
	}

	/** 논리 삭제를 위한 메서드 **/
	public void deleteAuction() {
		this.isDeleted = true;
	}
}
