package com.example.product_auction.product.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.product_auction.product.domain.Auction;
import com.example.product_auction.product.domain.Product;
import com.example.product_auction.product.repository.AuctionRepository;
import com.example.product_auction.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuctionServiceImpl implements AuctionService {

	private final AuctionRepository auctionRepository;
	private final ProductRepository productRepository;

	/**
	 * 전체경매 조회 (진행중, 완료된 경매) 상관없이 모두 조회
	 * @return
	 */
	@Override
	public List<Auction.AuctionBaseResponse> findAllByAuctions() {
		List<Auction> allAuctions = auctionRepository.findAll();
		ArrayList<Auction.AuctionBaseResponse> auctions = new ArrayList<>();

		for (Auction.AuctionBaseResponse auction : auctions) {
			Auction.AuctionBaseResponse response =
				Auction.AuctionBaseResponse.builder()
					.id(auction.getId())
					.productId(auction.getProductId())
					.startTime(auction.getStartTime())
					.endTime(auction.getEndTime())
					.status(auction.getStatus())
					.highestBid(auction.getHighestBid())
					.build();
			auctions.add(response);
		}
		return auctions;
	}

	/**
	 * 진행중인 경매 조회
	 * @return
	 */
	@Override
	public List<Auction.OngoingAuctionResponse> getOngoingAuctions() {
		List<Auction> ongoingAuctions = auctionRepository.findByStatusWithProduct(Auction.AuctionStatus.ONGOING);
		List<Auction.OngoingAuctionResponse> responses = new ArrayList<>();

		for (Auction auction : ongoingAuctions) {
			Auction.OngoingAuctionResponse response = Auction.OngoingAuctionResponse.builder()
				.id(auction.getId())
				.productId(auction.getProduct().getId())
				.startTime(auction.getStartTime())
				.endTime(auction.getEndTime())
				.highestBid(auction.getHighestBid())
				.status(auction.getStatus())
				.productName(auction.getProduct().getName())
				.build();
			responses.add(response);
		}

		return responses;
	}

	/**
	 * 종료된 경매 조회
	 * @return
	 */
	@Override
	public List<Auction.ClosedAuctionResponse> getClosedAuctions() {
		List<Auction> closedAuctions = auctionRepository.findByStatusWithProduct(Auction.AuctionStatus.CLOSED);
		List<Auction.ClosedAuctionResponse> responses = new ArrayList<>();

		for (Auction auction : closedAuctions) {
			Auction.ClosedAuctionResponse response = Auction.ClosedAuctionResponse.builder()
				.id(auction.getId())
				.productId(auction.getProduct().getId())
				.startTime(auction.getStartTime())
				.endTime(auction.getEndTime())
				.highestBid(auction.getHighestBid())
				.status(auction.getStatus())
				.productName(auction.getProduct().getName())
				.winnerId(auction.getWinnerId())
				.build();
			responses.add(response);
		}

		return responses;
	}

	@Override
	@Transactional
	public Auction.RegisterAuctionResponse registerAuction(Auction.RegisterAuctionRequest request) {
		Product product = request.getProduct();

		if (product == null) {
			throw new IllegalArgumentException("상품 정보가 없습니다.");
		}

		// 입력값 확인
		log.info("경매 등록 요청 - product: {}", product);
		log.info("상품 이름: {}", product.getName());
		log.info("상품 시작 가격: {}", product.getStartPrice());
		log.info("경매 설명: {}", request.getDescription());
		log.info("경매 시작 시간: {}", request.getStartTime());
		log.info("경매 종료 시간: {}", request.getEndTime());

		// 상품 등록
		Product savedProduct = productRepository.save(product);
		productRepository.flush();  // 강제 반영 (선택적)

		log.info("저장된 Product ID: {}", savedProduct.getId());
		log.info("저장된 상품 이름: {}", savedProduct.getName());

		// 경매 생성
		Auction auction = Auction.builder()
			.product(savedProduct)
			.description(request.getDescription())
			.startTime(request.getStartTime())
			.endTime(request.getEndTime())
			.status(Auction.AuctionStatus.ONGOING)
			.highestBid(0L)
			.build();

		Auction savedAuction = auctionRepository.save(auction);

		log.info("저장된 Auction ID: {}", savedAuction.getId());
		log.info("저장된 경매 상태: {}", savedAuction.getStatus());
		log.info("저장된 경매 product_id: {}", savedAuction.getProduct().getId());

		return Auction.RegisterAuctionResponse.builder()
			.auctionId(savedAuction.getId())
			.product(savedProduct)
			.description(savedAuction.getDescription())
			.startTime(savedAuction.getStartTime())
			.endTime(savedAuction.getEndTime())
			.status(savedAuction.getStatus())
			.build();
	}

	/**
	 * 경매 삭제
	 * @param request
	 * @return
	 */
	/**
	 * 경매 삭제
	 */
	@Transactional
	public Auction.DeleteAuctionResponse deleteAuction(Auction.DeleteAuctionRequest request) {
		Optional<Auction> auctionOpt = auctionRepository.findById(request.getAuctionId());
		if (auctionOpt.isPresent()) {
			Auction auction = auctionOpt.get();
			auctionRepository.delete(auction); // 진짜 삭제
			log.info("경매 삭제 완료 auctionId = {}", auction.getId());
			return Auction.DeleteAuctionResponse.builder()
				.auctionId(auction.getId())
				.build();
		} else {
			log.warn("경매 삭제 실패: auctionId = {} (존재하지 않음)", request.getAuctionId());
			throw new IllegalArgumentException("해당 경매를 찾을 수 없습니다.");
		}
	}
}

