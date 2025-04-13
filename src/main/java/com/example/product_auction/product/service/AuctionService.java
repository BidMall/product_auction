package com.example.product_auction.product.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.product_auction.product.domain.Auction;
import com.example.product_auction.product.dto.AuctionRequest;
import com.example.product_auction.product.repository.AuctionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
@RequiredArgsConstructor
public class AuctionService {

	private final AuctionRepository auctionRepository;

	/** 단일조회 **/
	public Auction getAuctionById(Long id) {
		Optional<Auction> auctionOptional = auctionRepository.findById(id);
		if (auctionOptional.isPresent()) {
			return auctionOptional.get();
		} else {
			throw new RuntimeException("Auction now found Id" + id);
		}
	}

	//전체조회
	public List<Auction> getAllAuction() {
		List<Auction> auctionAll = auctionRepository.findAll();
		return auctionAll;
	}

	// 경매등록
	@Transactional
	public Auction createAuction(AuctionRequest auctionRequest) {
		// DTO 데이터를 엔티티로 변환
		Auction auction = Auction.builder()
			.productId(auctionRequest.getProductId())
			.startTime(LocalDateTime.now())
			.endTime(LocalDateTime.now().plusDays(7))
			.highestBid(0L) // 초기 입찰가는 0으로 설정
			.isClosed(false)
			.build();
		return auctionRepository.save(auction);
	}

	//경매수정
	@Transactional
	public Auction updateAuction(Long auctionId, AuctionRequest auctionRequest) {
		Auction auction = auctionRepository.findById(auctionId)
			.orElseThrow(() -> new IllegalArgumentException("해당 경매가 존재하지 않습니다."));
		Auction updatedAuction = auction.toBuilder()
			.productId(auctionRequest.getProductId())
			.startTime(LocalDateTime.now())
			.endTime(LocalDateTime.now().plusDays(7))
			.highestBid(0L)
			.isClosed(false)
			.build();
		return auctionRepository.save(updatedAuction);
	}

	// 경매 삭제 (논리 삭제)
	@Transactional
	public void deleteAuction(Long auctionId) {
		// 경매 조회
		Auction auction = auctionRepository.findById(auctionId)
			.orElseThrow(() -> new IllegalArgumentException("경매를 찾을 수 없습니다."));

		// 경매 삭제 처리 (논리 삭제)
		auction.deleteAuction();

		// 변경된 경매 정보 저장
		auctionRepository.save(auction);
	}

	// 경매 삭제 (영구 삭제)
	@Transactional
	public void deleteAuctionPermanently(Long auctionId) {
		auctionRepository.deleteById(auctionId);
	}
}
