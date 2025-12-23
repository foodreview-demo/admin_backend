package com.foodreview.domain.report.dto;

import com.foodreview.domain.report.entity.Report;
import com.foodreview.domain.report.entity.ReportReason;
import com.foodreview.domain.report.entity.ReportStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReportResponse {
    private Long id;
    private Long reviewId;
    private String reviewContent;
    private String reviewerName;
    private String reviewerEmail;
    private String restaurantName;
    private String reporterName;
    private String reporterEmail;
    private ReportReason reason;
    private String description;
    private ReportStatus status;
    private String adminNote;
    private String processedByName;
    private LocalDateTime createdAt;
    private LocalDateTime processedAt;

    public static ReportResponse from(Report report) {
        return ReportResponse.builder()
                .id(report.getId())
                .reviewId(report.getReview().getId())
                .reviewContent(report.getReview().getContent())
                .reviewerName(report.getReview().getUser().getName())
                .reviewerEmail(report.getReview().getUser().getEmail())
                .restaurantName(report.getReview().getRestaurant().getName())
                .reporterName(report.getReporter().getName())
                .reporterEmail(report.getReporter().getEmail())
                .reason(report.getReason())
                .description(report.getDescription())
                .status(report.getStatus())
                .adminNote(report.getAdminNote())
                .processedByName(report.getProcessedBy() != null ? report.getProcessedBy().getName() : null)
                .createdAt(report.getCreatedAt())
                .processedAt(report.getUpdatedAt())
                .build();
    }
}
