package com.foodreview.domain.review.entity;

/**
 * 영수증 검증 상태
 */
public enum ReceiptVerificationStatus {
    NONE,
    PENDING,
    VERIFIED,
    REJECTED,
    PENDING_REVIEW,
    MANUALLY_APPROVED,
    MANUALLY_REJECTED
}
