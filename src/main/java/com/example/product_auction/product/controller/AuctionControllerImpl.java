package com.example.product_auction.product.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.product_auction.product.domain.Auction;
import com.example.product_auction.product.service.AuctionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auctions")
@RequiredArgsConstructor
@Slf4j
public class AuctionControllerImpl implements AuctionController {

	private final AuctionService auctionService;

	/**
	 * 전체 경매 목록 조회 (진행 중, 종료된 경매 모두 포함)
	 *
	 * @return 전체 경매 목록
	 */
	@Override
	@GetMapping
	public ResponseEntity<List<Auction.AuctionBaseResponse>> getAllAuctions() {
		try {
			List<Auction.AuctionBaseResponse> auctions = auctionService.findAllByAuctions();
			log.info("경매 목록 조회 성공 총 ={}건", auctions.size());
			return ResponseEntity.ok(auctions);
		} catch (Exception e) {
			log.error("경매 목록 조회 실패={}", e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
}
