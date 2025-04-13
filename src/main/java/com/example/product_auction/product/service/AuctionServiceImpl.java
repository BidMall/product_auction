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
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuctionServiceImpl implements AuctionService {

	private final AuctionRepository auctionRepository;

	/** 단일조회 **/
	@Override
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

	@Transactional
	@Override
	public Auction createAuction(AuctionRequest auctionRequest) {
		try {
			Auction auction = Auction.builder()
				.productId(auctionRequest.getProductId())
				.startTime(LocalDateTime.now())
				.endTime(LocalDateTime.now().plusDays(7))
				.highestBid(0L)
				.isClosed(false)
				.build();
			return auctionRepository.save(auction);
		} catch (Exception e) {
			log.error("Failed to create auction", e);
			throw new RuntimeException("경매 생성 실패", e); // 예외를 던져서 컨트롤러에서 처리할 수 있게
		}
	}

	@Transactional
	@Override
	public Auction updateAuction(Long auctionId, AuctionRequest auctionRequest) {
		try {
			Optional<Auction> auction = auctionRepository.findById(auctionId);
			if (auction.isEmpty()) {
				throw new IllegalArgumentException("경매를 찾을 수 없습니다.");
			}
			Auction updatedAuction = auction.get().toBuilder()
				.productId(auctionRequest.getProductId())
				.startTime(LocalDateTime.now())
				.endTime(LocalDateTime.now().plusDays(7))
				.highestBid(0L) // 초기 입찰가는 0으로 설정
				.isClosed(false)
				.build();
			return auctionRepository.save(updatedAuction);
		} catch (Exception e) {
			log.error("경매 수정 중 오류 발생", e);
			throw new RuntimeException("경매 수정에 실패했습니다.", e);
		}
	}

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
			throw new RuntimeException("경매 삭제에 실패했습니다.", e); // 적절한 예외를 던짐
		}
	}

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
	 * // 경매 상태에 따른 조회 (isClosed: true 또는 false)
	 * @param isClosed
	 * @return
	 */
	@Override
	public List<Auction> getAuctionsByClosedStatus(boolean isClosed) {
		try {
			return auctionRepository.findByIsClosed(isClosed);
		} catch (Exception e) {
			log.error("경매 상태 조회 중 오류 발생", e);
			throw new RuntimeException("경매 상태 조회 실패", e);
		}
	}

	/**
	 * // 특정 상품 ID에 해당하는 경매 조회
	 * @param productId
	 * @return
	 */
	public List<Auction> getAuctionsByProductId(Long productId) {
		try {
			return auctionRepository.findByProductId(productId);
		} catch (Exception e) {
			log.error("상품 ID로 경매 조회 중 오류 발생", e);
			throw new RuntimeException("상품 경매 조회 실패", e);
		}
	}
}
