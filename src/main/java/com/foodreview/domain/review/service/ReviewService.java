package com.foodreview.domain.review.service;

import com.foodreview.domain.review.dto.ReceiptReviewResponse;
import com.foodreview.domain.review.entity.ReceiptVerificationStatus;
import com.foodreview.domain.review.entity.Review;
import com.foodreview.domain.review.repository.ReviewRepository;
import com.foodreview.global.common.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Admin 리뷰 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;

    /**
     * 수동 검토 대기 중인 영수증 리뷰 목록 조회
     */
    public PageResponse<ReceiptReviewResponse> getPendingReceiptReviews(Pageable pageable) {
        Page<Review> reviews = reviewRepository.findByReceiptVerificationStatus(
                ReceiptVerificationStatus.PENDING_REVIEW, pageable);

        List<ReceiptReviewResponse> content = reviews.getContent().stream()
                .map(ReceiptReviewResponse::from)
                .toList();

        return PageResponse.from(reviews, content);
    }

    /**
     * 영수증 수동 승인
     */
    @Transactional
    public void approveReceipt(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다: " + reviewId));

        review.approveReceiptManually();
        log.info("영수증 수동 승인 완료. reviewId: {}", reviewId);
    }

    /**
     * 영수증 수동 거부
     */
    @Transactional
    public void rejectReceipt(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다: " + reviewId));

        review.rejectReceiptManually();
        log.info("영수증 수동 거부 완료. reviewId: {}", reviewId);
    }

    /**
     * 영수증 검토 대기 수
     */
    public long getPendingReceiptCount() {
        return reviewRepository.countByReceiptVerificationStatus(ReceiptVerificationStatus.PENDING_REVIEW);
    }
}
