package com.example.product_auction.product.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.product_auction.product.domain.Auction;
import com.example.product_auction.product.dto.AuctionRequest;
import com.example.product_auction.product.dto.AuctionResponse;
import com.example.product_auction.product.repository.AuctionRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuctionServiceImpl implements AuctionService {

	private final AuctionRepository auctionRepository;

	/**
	 * 단일 경매 상세 조회
	 * @param id
	 * @return
	 */
	@Override
	public AuctionResponse getAuctionById(Long id) {
		Optional<Auction> auctionOptional = auctionRepository.findById(id);

		Auction auction = auctionOptional.orElseThrow(() ->
			new EntityNotFoundException("Auction not found with id: " + id));

		return AuctionResponse.builder()
			.id(auction.getId())
			.productId(auction.getProductId())
			.productName("상품 이름")  // 실제 상품 이름 설정 필요
			.productDescription("상품 설명")  // 실제 상품 설명 설정 필요
			.startTime(auction.getStartTime())
			.endTime(auction.getEndTime())
			.highestBid(auction.getHighestBid())
			.winnerId(auction.getWinnerId())
			.isClosed(auction.getEndTime().isBefore(LocalDateTime.now()))
			.build();
	}

	/**
	 * 전체 경매 목록 조회
	 * @return
	 */
	@Override
	public List<AuctionResponse> getAllAuction() {
		List<Auction> auctions = auctionRepository.findAll();

		return auctions.stream()
			.map(auction -> AuctionResponse.builder()
				.id(auction.getId())
				.productId(auction.getProductId())
				.productName("상품 이름")
				.productDescription("상품 설명")
				.startTime(auction.getStartTime())
				.endTime(auction.getEndTime())
				.highestBid(auction.getHighestBid())
				.winnerId(auction.getWinnerId())
				.isClosed(auction.getEndTime().isBefore(LocalDateTime.now()))
				.build())
			.collect(Collectors.toList());
	}

	/**
	 * 경매 생성
	 * @param auctionRequest
	 * @return
	 */
	@Transactional
	@Override
	public AuctionResponse createAuction(AuctionRequest auctionRequest) {
		try {
			Auction auction = Auction.builder()
				.productId(auctionRequest.getProductId())
				.startTime(LocalDateTime.now())
				.endTime(LocalDateTime.now().plusDays(7))
				.highestBid(0L)
				.isClosed(false)
				.build();
			Auction savedAuction = auctionRepository.save(auction);

			return AuctionResponse.builder()
				.id(savedAuction.getId())
				.productId(savedAuction.getProductId())
				.productName("상품 이름")  // 상품 정보 반영
				.productDescription("상품 설명")  // 상품 설명 반영
				.startTime(savedAuction.getStartTime())
				.endTime(savedAuction.getEndTime())
				.highestBid(savedAuction.getHighestBid())
				.winnerId(savedAuction.getWinnerId())
				.isClosed(savedAuction.getEndTime().isBefore(LocalDateTime.now()))
				.build();
		} catch (Exception e) {
			log.error("Failed to create auction", e);
			throw new RuntimeException("경매 생성 실패", e);
		}
	}

	/**
	 * 경매 수정
	 * @param auctionId
	 * @param auctionRequest
	 * @return
	 */
	@Transactional
	@Override
	public AuctionResponse updateAuction(Long auctionId, AuctionRequest auctionRequest) {
		try {
			Optional<Auction> auctionOptional = auctionRepository.findById(auctionId);
			if (auctionOptional.isEmpty()) {
				throw new IllegalArgumentException("경매를 찾을 수 없습니다.");
			}
			Auction updatedAuction = auctionOptional.get().toBuilder()
				.productId(auctionRequest.getProductId())
				.startTime(LocalDateTime.now())
				.endTime(LocalDateTime.now().plusDays(7))
				.highestBid(0L)  // 초기 입찰가는 0으로 설정
				.isClosed(false)
				.build();

			Auction savedAuction = auctionRepository.save(updatedAuction);

			return AuctionResponse.builder()
				.id(savedAuction.getId())
				.productId(savedAuction.getProductId())
				.productName("상품 이름")  // 상품 정보 반영
				.productDescription("상품 설명")  // 상품 설명 반영
				.startTime(savedAuction.getStartTime())
				.endTime(savedAuction.getEndTime())
				.highestBid(savedAuction.getHighestBid())
				.winnerId(savedAuction.getWinnerId())
				.isClosed(savedAuction.getEndTime().isBefore(LocalDateTime.now()))
				.build();
		} catch (Exception e) {
			log.error("경매 수정 중 오류 발생", e);
			throw new RuntimeException("경매 수정에 실패했습니다.", e);
		}
	}

	/**
	 * 논리 삭제
	 * @param auctionId
	 */
	@Transactional
	@Override
	public void deleteAuction(Long auctionId) {
		try {
			Optional<Auction> auctionOptional = auctionRepository.findById(auctionId);
			if (auctionOptional.isEmpty()) {
				throw new IllegalArgumentException("경매를 찾을 수 없습니다.");
			}
			Auction auction = auctionOptional.get();

			auction.deleteAuction();

			auctionRepository.save(auction);
		} catch (Exception e) {
			log.error("경매 삭제 중 오류 발생", e);
			throw new RuntimeException("경매 삭제에 실패했습니다.", e);
		}
	}

	/**
	 * 영구 삭제
	 * @param auctionId
	 */
	@Transactional
	@Override
	public void deleteAuctionPermanently(Long auctionId) {
		try {
			auctionRepository.deleteById(auctionId);
		} catch (Exception e) {
			log.error("경매 삭제 중 오류 발생", e);
			throw new RuntimeException("경매 삭제에 실패했습니다.", e);
		}
	}

	/**
	 * 경매 종료 여부에 따른 조회 (isClosed: true 또는 false)
	 * @param isClosed
	 * @return
	 */
	@Override
	public List<AuctionResponse> getAuctionsByClosedStatus(boolean isClosed) {
		try {
			List<Auction> auctions = auctionRepository.findByIsClosed(isClosed);
			return auctions.stream()
				.map(auction -> AuctionResponse.builder()
					.id(auction.getId())
					.productId(auction.getProductId())
					.productName("상품 이름")
					.productDescription("상품 설명")
					.startTime(auction.getStartTime())
					.endTime(auction.getEndTime())
					.highestBid(auction.getHighestBid())
					.winnerId(auction.getWinnerId())
					.isClosed(auction.getEndTime().isBefore(LocalDateTime.now()))
					.build())
				.collect(Collectors.toList());
		} catch (Exception e) {
			log.error("경매 상태 조회 중 오류 발생", e);
			throw new RuntimeException("경매 상태 조회 실패", e);
		}
	}
}
