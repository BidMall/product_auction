package com.example.product_auction.product.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.product_auction.product.dto.AuctionRequest;
import com.example.product_auction.product.dto.AuctionResponse;
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
	 * 단일 경매 조회
	 * @param id
	 * @return
	 */
	@Override
	@GetMapping("/{id}")
	public ResponseEntity<AuctionResponse> getAuction(
		@PathVariable Long id) {
		try {
			AuctionResponse auctionById = auctionService.getAuctionById(id);
			log.info("단일 경매 조회={}", auctionById);
			return ResponseEntity.ok(auctionById);
		} catch (Exception e) {
			log.error("단일 경매 실패={}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	/**
	 * 전체 경매 목록 조회
	 * @return
	 */
	@Override
	@GetMapping
	public ResponseEntity<List<AuctionResponse>> getAllAuctions() {
		try {
			List<AuctionResponse> auctions = auctionService.getAllAuction();
			log.info("경매 목록 조회={}", auctions);
			return ResponseEntity.ok(auctions);
		} catch (Exception e) {
			log.error("경매 생성 실패={}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	/**
	 * 경매생성
	 * @param request
	 * @return
	 */
	@Override
	@PostMapping
	public ResponseEntity<AuctionResponse> createAuction(
		@RequestBody AuctionRequest request) {
		try {
			AuctionResponse auction = auctionService.createAuction(request);
			log.info("경매 성공 성공={}", auction);
			return ResponseEntity.status(HttpStatus.CREATED).body(auction);
		} catch (Exception e) {
			log.error("경매 생성 실패={}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	/**
	 * 경매 수정
	 * @param id
	 * @param request
	 * @return
	 */
	@Override
	@PutMapping("/{id}")
	public ResponseEntity<AuctionResponse> updateAuction(
		@PathVariable Long id,
		@RequestBody AuctionRequest request) {
		try {
			AuctionResponse auctionResponse = auctionService.updateAuction(id, request);
			log.info("경매 수정 성공={}", auctionResponse);
			return ResponseEntity.status(HttpStatus.CREATED).body(auctionResponse);
		} catch (Exception e) {
			log.error("경매 수정 실패={}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	/**
	 * 경매 논리 삭제
	 * @param id
	 * @return
	 */
	@Override
	@PatchMapping("/{id}/delete")
	public ResponseEntity<Void> deleteAuctionLogic(@PathVariable Long id) {
		try {
			auctionService.deleteAuction(id);
			log.info("경매 논리 삭제 성공: " + id);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			log.error("경매 논리 삭제 실패", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * 경매 영구 삭제
	 * @param id
	 * @return
	 */
	@Override
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAuctionPermanently(@PathVariable Long id) {
		try {
			auctionService.deleteAuctionPermanently(id);
			log.info("경매 영구 삭제 성공: " + id);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			log.error("경매 영구 삭제 실패", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * 경매 상태(isClosed)에 따른 조회
	 * @param isClosed
	 * @return
	 */
	@Override
	@GetMapping("/status")
	public ResponseEntity<List<AuctionResponse>> getAuctionsByStatus(@RequestParam boolean isClosed) {
		try {
			List<AuctionResponse> auctions = auctionService.getAuctionsByClosedStatus(isClosed);
			log.info("경매 상태 조회 성공, isClosed: {}", isClosed);
			return ResponseEntity.ok(auctions);
		} catch (Exception e) {
			log.error("경매 상태 조회 실패, isClosed: {}", isClosed, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
