package com.foodreview.domain.review.dto;

import com.foodreview.domain.review.entity.ReceiptVerificationStatus;
import com.foodreview.domain.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 영수증 검토 대기 리뷰 응답 DTO
 */
@Getter
@Builder
@AllArgsConstructor
public class ReceiptReviewResponse {

    private Long id;
    private Long userId;
    private String userName;
    private String userEmail;
    private Long restaurantId;
    private String restaurantName;
    private String content;
    private String receiptImageUrl;
    private ReceiptVerificationStatus verificationStatus;
    private Integer verificationScore;
    private String ocrText;
    private LocalDateTime createdAt;

    public static ReceiptReviewResponse from(Review review) {
        return ReceiptReviewResponse.builder()
                .id(review.getId())
                .userId(review.getUser().getId())
                .userName(review.getUser().getName())
                .userEmail(review.getUser().getEmail())
                .restaurantId(review.getRestaurant().getId())
                .restaurantName(review.getRestaurant().getName())
                .content(review.getContent())
                .receiptImageUrl(review.getReceiptImageUrl())
                .verificationStatus(review.getReceiptVerificationStatus())
                .verificationScore(review.getReceiptVerificationScore())
                .ocrText(review.getReceiptOcrText())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
