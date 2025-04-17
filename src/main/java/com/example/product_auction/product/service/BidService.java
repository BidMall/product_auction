package com.example.product_auction.product.service;

import java.util.List;

import com.example.product_auction.product.domain.Auction;
import com.example.product_auction.product.domain.Bid;

public interface BidService {

	List<Bid.BidResponse> getBidsByAuctionId(Bid.BidListRequest request);

	List<Bid.BidResponse> getOnGoingBid(Bid.BidListRequest request, Auction.AuctionStatus status);

}
