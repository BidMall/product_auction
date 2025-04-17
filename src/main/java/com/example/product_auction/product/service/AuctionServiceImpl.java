package com.example.product_auction.product.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.product_auction.product.domain.Auction;
import com.example.product_auction.product.repository.AuctionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuctionServiceImpl implements AuctionService {

	private final AuctionRepository auctionRepository;

	// 진행중, 종료와 관계없이 전체 경매 조회

	/**
	 * 진행중인 경매 조회
	 * @return
	 */
	@Override
	public List<Auction.OngoingAuctionResponse> findByOnGoingAuctions() {
		List<Auction> auctions = auctionRepository.findByIsClosedFalseAndIsDeletedFalse();

		return auctions.stream()
			.map(auction -> Auction.OngoingAuctionResponse.builder()
				.id(auction.getId())
				.productId(auction.getProductId())
				.startTime(auction.getStartTime())
				.endTime(auction.getEndTime())
				.highestBid(auction.getHighestBid())
				.isDeleted(auction.getIsDeleted())
				.productName(auction.getProduct().getName())
				.isClosed(auction.getIsClosed())
				.build()
			)
			.collect(Collectors.toList());
	}

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
					.highestBid(auction.getHighestBid())
					.isDeleted(auction.getIsDeleted())
					.build();
			auctions.add(response);
		}
		return auctions;
	}

	/**
	 * 종료된 경매 조회
	 */
	@Override
	public List<Auction.ClosedAuctionResponse> findEndByAuctions() {
		List<Auction> closedAuctions = auctionRepository.findByIsClosedTrue();
		ArrayList<Auction.ClosedAuctionResponse> auctions = new ArrayList<>();

		for (Auction.ClosedAuctionResponse auction : auctions) {
			Auction.ClosedAuctionResponse closedAuctionResponse = Auction.ClosedAuctionResponse.builder()
				.id(auction.getId())
				.productId(auction.getProductId())
				.startTime(auction.getStartTime())
				.endTime(auction.getEndTime())
				.highestBid(auction.getHighestBid())
				.isDeleted(auction.getIsDeleted())
				.build();
			auctions.add(closedAuctionResponse);
		}
		return auctions;
	}
}

