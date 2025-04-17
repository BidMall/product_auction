package com.example.product_auction.product.service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.example.product_auction.product.domain.Auction;
import com.example.product_auction.product.domain.Bid;
import com.example.product_auction.product.dto.BidStatusResponse;
import com.example.product_auction.product.repository.AuctionRepository;
import com.example.product_auction.product.repository.BidRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class BidService {

	private final BidRepository bidRepository;
	private final AuctionRepository auctionRepository;

	//실시간 입찰자 조회
	public Bid findByBidId(Long bidId) {
		Optional<Bid> bid = bidRepository.findById(bidId);
		if (bid.isPresent()) {
			return bid.get();
		} else {
			throw new RuntimeException("입찰자가 없습니다");
		}
	}

	/**
	 * 경매에서 가장 높은 입찰 금액을 제시한 최고 입찰자 반환
	 * @param auctionId
	 * @return
	 */
	public BidStatusResponse getHighestBidderByAuctionId(Long auctionId) {
		List<Bid> bids = bidRepository.findByAuctionId(auctionId);

		LocalDateTime currentTime = LocalDateTime.now();
		boolean isClosed = false;
		LocalDateTime endTime = null;

		Optional<Auction> auction = auctionRepository.findById(auctionId);
		if (auction.isPresent()) {
			endTime = auction.get().getEndTime();
			isClosed = currentTime.isAfter(endTime);
		}

		Bid highestBid = bids.stream()
			.max(Comparator.comparing(Bid::getBidAmount))
			.orElseThrow(() -> new RuntimeException("입찰 내역이 없습니다."));

		return BidStatusResponse.builder()
			.auctionId(auctionId)
			.highestBid(highestBid.getBidAmount())
			.highestBidderId(highestBid.getBidderId())
			.bidCount(bids.size())
			.isClosed(isClosed)
			.endTime(endTime)
			.build();
	}
}
