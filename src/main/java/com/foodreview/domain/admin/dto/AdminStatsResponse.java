package com.foodreview.domain.admin.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminStatsResponse {
    private long totalUsers;
    private long totalReviews;
    private long totalRestaurants;
    private long pendingReports;
    private long pendingChatReports;
}
