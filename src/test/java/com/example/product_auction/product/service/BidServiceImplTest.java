package com.example.product_auction.product.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.product_auction.product.domain.Auction;
import com.example.product_auction.product.domain.Bid;
import com.example.product_auction.product.repository.BidRepository;

@ExtendWith(MockitoExtension.class)
class BidServiceImplTest {

	@Mock
	private BidRepository bidRepository;

	@InjectMocks
	private BidServiceImpl bidService;

	private List<Bid> sampleBids;

	@BeforeEach
	void setUp() {
		Auction auction = Auction.builder()
			.id(0L)
			.status(Auction.AuctionStatus.ONGOING)
			.build();

		sampleBids = List.of(
			Bid.builder()
				.auction(auction)
				.bidderId(2L)
				.bidAmount(1000L)
				.bidTime(LocalDateTime.now())
				.build()
		);
	}

	@Test
	@DisplayName("경매 ID로 전체 입찰 목록을 조회할 수 있다")
	void 전체_입찰_조회() throws Exception {

		// given
		when(bidRepository.findByAuctionId(0L)).thenReturn(sampleBids);

		//when
		List<Bid.BidResponse> bidIdResponse = bidService.getBidsByAuctionId(
			Bid.BidListRequest.builder().auctionId(0L).build()
		);

		//then
		assertNotNull(bidIdResponse);
		assertEquals(1, bidIdResponse.size());
		assertEquals(0L, bidIdResponse.get(0).getAuctionId());
		assertEquals(1000L, bidIdResponse.get(0).getBidAmount());
	}

	@Test
	@DisplayName("경매 ID와 상태로 진행 중인 입찰 목록을 조회할 수 있다")
	void 진행중_입찰_조회() throws Exception {

		//given
		when(bidRepository.findByAuctionIdAndAuctionStatus(0L, Auction.AuctionStatus.ONGOING))
			.thenReturn(sampleBids);

		//when
		List<Bid.BidResponse> BidStatusResponse = bidService.getOnGoingBid(
			Bid.BidListRequest.builder().auctionId(0L).build(),
			Auction.AuctionStatus.ONGOING
		);

		//then
		assertNotNull(BidStatusResponse);
		assertEquals(1, BidStatusResponse.size());
		assertEquals(0L, BidStatusResponse.get(0).getAuctionId());
		assertEquals(1000L, BidStatusResponse.get(0).getBidAmount());
	}
}
