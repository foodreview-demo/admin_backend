package com.foodreview.domain.gathering.entity;

public enum RefundType {
    AUTO("자동환금"),
    MANUAL("수동환금");

    private final String displayName;

    RefundType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
