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

import com.example.product_auction.product.domain.Auction;
import com.example.product_auction.product.dto.AuctionRequest;
import com.example.product_auction.product.service.AuctionServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auctions")
@RequiredArgsConstructor
public class AuctionController {

	private final AuctionServiceImpl auctionService;

	/** 단일 경매 조회 **/
	@GetMapping("/{id}")
	public ResponseEntity<Auction> getAuction(@PathVariable Long id) {
		Auction auction = auctionService.getAuctionById(id);
		return ResponseEntity.ok(auction);
	}

	/** 전체 경매 목록 조회 **/
	@GetMapping
	public ResponseEntity<List<Auction>> getAllAuctions() {
		List<Auction> auctions = auctionService.getAllAuction();
		return ResponseEntity.ok(auctions);
	}

	/** 경매 생성 **/
	@PostMapping
	public ResponseEntity<Auction> createAuction(@RequestBody AuctionRequest request) {
		Auction createdAuction = auctionService.createAuction(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdAuction);
	}

	/** 경매 수정 **/
	@PutMapping("/{id}")
	public ResponseEntity<Auction> updateAuction(@PathVariable Long id, @RequestBody AuctionRequest request) {
		Auction updatedAuction = auctionService.updateAuction(id, request);
		return ResponseEntity.ok(updatedAuction);
	}

	/** 경매 논리 삭제 **/
	@PatchMapping("/{id}/delete")
	public ResponseEntity<Void> deleteAuction(@PathVariable Long id) {
		auctionService.deleteAuction(id);
		return ResponseEntity.noContent().build();
	}

	/** 경매 영구 삭제 **/
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAuctionPermanently(@PathVariable Long id) {
		auctionService.deleteAuctionPermanently(id);
		return ResponseEntity.noContent().build();
	}

	/** 경매 상태(isClosed)에 따른 조회 **/
	@GetMapping("/status")
	public ResponseEntity<List<Auction>> getAuctionsByStatus(@RequestParam boolean isClosed) {
		List<Auction> auctions = auctionService.getAuctionsByClosedStatus(isClosed);
		return ResponseEntity.ok(auctions);
	}

	/** 특정 상품 ID에 해당하는 경매 목록 조회 **/
	@GetMapping("/product/{productId}")
	public ResponseEntity<List<Auction>> getAuctionsByProductId(@PathVariable Long productId) {
		List<Auction> auctions = auctionService.getAuctionsByProductId(productId);
		return ResponseEntity.ok(auctions);
	}
}
