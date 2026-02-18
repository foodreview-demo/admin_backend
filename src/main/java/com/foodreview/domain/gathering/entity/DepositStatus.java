package com.foodreview.domain.gathering.entity;

public enum DepositStatus {
    PENDING("대기중"),
    DEPOSITED("입금완료"),
    REFUNDED("환금완료"),
    REFUND_FAILED("환금실패");

    private final String displayName;

    DepositStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
