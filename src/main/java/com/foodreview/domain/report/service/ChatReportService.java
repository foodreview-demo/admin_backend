package com.foodreview.domain.report.service;

import com.foodreview.domain.report.dto.ChatReportResponse;
import com.foodreview.domain.report.dto.ReportProcessRequest;
import com.foodreview.domain.report.entity.ChatReport;
import com.foodreview.domain.report.entity.ReportStatus;
import com.foodreview.domain.report.repository.ChatReportRepository;
import com.foodreview.domain.user.entity.User;
import com.foodreview.global.common.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatReportService {

    private final ChatReportRepository chatReportRepository;

    public PageResponse<ChatReportResponse> getChatReports(ReportStatus status, Pageable pageable) {
        Page<ChatReport> reports;
        if (status != null) {
            reports = chatReportRepository.findByStatus(status, pageable);
        } else {
            reports = chatReportRepository.findAll(pageable);
        }

        List<ChatReportResponse> content = reports.getContent().stream()
                .map(ChatReportResponse::from)
                .toList();

        return PageResponse.from(reports, content);
    }

    public ChatReportResponse getChatReport(Long reportId) {
        ChatReport report = chatReportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("신고를 찾을 수 없습니다"));
        return ChatReportResponse.from(report);
    }

    @Transactional
    public ChatReportResponse processChatReport(Long reportId, User admin, ReportProcessRequest request) {
        ChatReport report = chatReportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("신고를 찾을 수 없습니다"));

        if (request.getAction() == ReportProcessRequest.ProcessAction.RESOLVE) {
            report.resolve(admin, request.getAdminNote());
        } else if (request.getAction() == ReportProcessRequest.ProcessAction.REJECT) {
            report.reject(admin, request.getAdminNote());
        }

        log.info("채팅 신고 처리: reportId={}, action={}, adminId={}",
                reportId, request.getAction(), admin.getId());

        return ChatReportResponse.from(report);
    }

    public long getPendingChatReportCount() {
        return chatReportRepository.countByStatus(ReportStatus.PENDING);
    }
}
