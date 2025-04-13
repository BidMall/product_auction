package com.example.product_auction.product.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.product_auction.product.dto.AuctionRequest;
import com.example.product_auction.product.dto.AuctionResponse;

@RestController
public interface AuctionController {

	@GetMapping("/{id}")
	ResponseEntity<AuctionResponse> getAuction(@PathVariable Long id);

	@GetMapping
	ResponseEntity<List<AuctionResponse>> getAllAuctions();

	@PostMapping
	ResponseEntity<AuctionResponse> createAuction(@RequestBody AuctionRequest request);

	@PutMapping("/{id}")
	ResponseEntity<AuctionResponse> updateAuction(@PathVariable Long id, @RequestBody AuctionRequest request);

	@PatchMapping("/{id}/delete")
	ResponseEntity<Void> deleteAuctionLogic(@PathVariable Long id);

	@DeleteMapping("/{id}")
	ResponseEntity<Void> deleteAuctionPermanently(@PathVariable Long id);

	@GetMapping("/status")
	ResponseEntity<List<AuctionResponse>> getAuctionsByStatus(@RequestParam boolean isClosed);
}
