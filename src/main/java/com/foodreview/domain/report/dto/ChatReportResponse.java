package com.foodreview.domain.report.dto;

import com.foodreview.domain.report.entity.ChatReport;
import com.foodreview.domain.report.entity.ChatReportReason;
import com.foodreview.domain.report.entity.ReportStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ChatReportResponse {
    private Long id;
    private Long reporterId;
    private String reporterName;
    private String reporterEmail;
    private Long reportedUserId;
    private String reportedUserName;
    private String reportedUserEmail;
    private Long chatRoomId;
    private String chatRoomUuid;
    private Long messageId;
    private String messageContent;
    private ChatReportReason reason;
    private String reasonDescription;
    private String description;
    private ReportStatus status;
    private String statusDescription;
    private String adminNote;
    private String processedByName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ChatReportResponse from(ChatReport report) {
        return ChatReportResponse.builder()
                .id(report.getId())
                .reporterId(report.getReporter().getId())
                .reporterName(report.getReporter().getName())
                .reporterEmail(report.getReporter().getEmail())
                .reportedUserId(report.getReportedUser().getId())
                .reportedUserName(report.getReportedUser().getName())
                .reportedUserEmail(report.getReportedUser().getEmail())
                .chatRoomId(report.getChatRoom().getId())
                .chatRoomUuid(report.getChatRoom().getUuid())
                .messageId(report.getMessageId())
                .messageContent(report.getMessageContent())
                .reason(report.getReason())
                .reasonDescription(report.getReason().getDescription())
                .description(report.getDescription())
                .status(report.getStatus())
                .statusDescription(report.getStatus().getDescription())
                .adminNote(report.getAdminNote())
                .processedByName(report.getProcessedBy() != null ? report.getProcessedBy().getName() : null)
                .createdAt(report.getCreatedAt())
                .updatedAt(report.getUpdatedAt())
                .build();
    }
}
