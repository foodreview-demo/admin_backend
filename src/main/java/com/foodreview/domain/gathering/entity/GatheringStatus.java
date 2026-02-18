package com.foodreview.domain.gathering.entity;

public enum GatheringStatus {
    RECRUITING("모집중"),
    CONFIRMED("확정"),
    IN_PROGRESS("진행중"),
    COMPLETED("완료"),
    CANCELLED("취소됨");

    private final String displayName;

    GatheringStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
