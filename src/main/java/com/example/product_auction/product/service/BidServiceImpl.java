package com.example.product_auction.product.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.product_auction.product.domain.Auction;
import com.example.product_auction.product.domain.Bid;
import com.example.product_auction.product.repository.BidRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class BidServiceImpl implements BidService {

	private final BidRepository bidRepository;

	/**
	 *
	 * @param request
	 * @return
	 */
	@Override
	public List<Bid.BidResponse> getBidsByAuctionId(Bid.BidListRequest request) {
		Long auctionId = request.getAuctionId();
		List<Bid> bids = bidRepository.findByAuctionId(auctionId);
		return getBidResponses(bids);
	}

	/**
	 * 진행 중인 경매에 대한 입찰 정보 조회
	 * @param request
	 * @param status
	 * @return
	 */
	@Override
	public List<Bid.BidResponse> getOnGoingBid(Bid.BidListRequest request, Auction.AuctionStatus status) {
		// 경매 상태가 ONGOING인 입찰 조회
		List<Bid> bids = bidRepository.findByAuctionIdAndAuctionStatus(request.getAuctionId(),
			Auction.AuctionStatus.ONGOING);
		return getBidResponses(bids);
	}

	/**
	 * 입찰 목록을 BidResponse로 변환하는 메서드
	 * @param bids
	 * @return
	 */
	private List<Bid.BidResponse> getBidResponses(List<Bid> bids) {
		List<Bid.BidResponse> bidResponses = new ArrayList<>();
		for (Bid bid : bids) {
			Bid.BidResponse bidResponse = Bid.BidResponse.builder()
				.auctionId(bid.getAuction().getId())
				.bidderId(bid.getBidderId())
				.bidAmount(bid.getBidAmount())
				.bidTime(bid.getBidTime())
				.build();
			bidResponses.add(bidResponse);
		}
		return bidResponses;
	}
}
