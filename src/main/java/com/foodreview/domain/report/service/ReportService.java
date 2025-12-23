package com.foodreview.domain.report.service;

import com.foodreview.domain.report.dto.ReportProcessRequest;
import com.foodreview.domain.report.dto.ReportResponse;
import com.foodreview.domain.report.entity.Report;
import com.foodreview.domain.report.entity.ReportStatus;
import com.foodreview.domain.report.repository.ReportRepository;
import com.foodreview.domain.review.entity.Review;
import com.foodreview.domain.review.repository.ReviewRepository;
import com.foodreview.domain.user.entity.User;
import com.foodreview.global.common.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {

    private final ReportRepository reportRepository;
    private final ReviewRepository reviewRepository;

    public PageResponse<ReportResponse> getReports(ReportStatus status, Pageable pageable) {
        Page<Report> reports;
        if (status != null) {
            reports = reportRepository.findByStatusWithDetails(status, pageable);
        } else {
            reports = reportRepository.findAllWithDetails(pageable);
        }
        return PageResponse.from(reports.map(ReportResponse::from));
    }

    public ReportResponse getReport(Long reportId) {
        Report report = reportRepository.findByIdWithDetails(reportId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 신고입니다"));
        return ReportResponse.from(report);
    }

    @Transactional
    public ReportResponse processReport(Long reportId, User admin, ReportProcessRequest request) {
        Report report = reportRepository.findByIdWithDetails(reportId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 신고입니다"));

        if (report.getStatus() != ReportStatus.PENDING) {
            throw new IllegalArgumentException("이미 처리된 신고입니다");
        }

        if (request.getAction() == ReportProcessRequest.ProcessAction.RESOLVE) {
            report.resolve(admin, request.getAdminNote());

            if (request.isDeleteReview()) {
                deleteReviewWithRelations(report.getReview());
            }
        } else {
            report.reject(admin, request.getAdminNote());
        }

        return ReportResponse.from(report);
    }

    public long getPendingReportCount() {
        return reportRepository.countByStatus(ReportStatus.PENDING);
    }

    private void deleteReviewWithRelations(Review review) {
        Long reviewId = review.getId();
        log.info("Deleting review: reviewId={}", reviewId);

        // 이 리뷰에 대한 신고들 삭제
        reportRepository.deleteByReviewId(reviewId);

        // 리뷰 삭제
        reviewRepository.delete(review);

        log.info("Review deleted: reviewId={}", reviewId);
    }
}
