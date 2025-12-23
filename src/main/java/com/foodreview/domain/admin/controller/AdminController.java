package com.foodreview.domain.admin.controller;

import com.foodreview.domain.admin.dto.AdminStatsResponse;
import com.foodreview.domain.report.dto.ReportProcessRequest;
import com.foodreview.domain.report.dto.ReportResponse;
import com.foodreview.domain.report.entity.ReportStatus;
import com.foodreview.domain.report.service.ReportService;
import com.foodreview.domain.restaurant.repository.RestaurantRepository;
import com.foodreview.domain.review.repository.ReviewRepository;
import com.foodreview.domain.user.repository.UserRepository;
import com.foodreview.global.common.ApiResponse;
import com.foodreview.global.common.PageResponse;
import com.foodreview.global.security.CurrentUser;
import com.foodreview.global.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final ReportService reportService;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<AdminStatsResponse>> getStats() {
        AdminStatsResponse stats = AdminStatsResponse.builder()
                .pendingReports(reportService.getPendingReportCount())
                .totalUsers(userRepository.count())
                .totalReviews(reviewRepository.count())
                .totalRestaurants(restaurantRepository.count())
                .build();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }

    @GetMapping("/reports")
    public ResponseEntity<ApiResponse<PageResponse<ReportResponse>>> getReports(
            @RequestParam(required = false) ReportStatus status,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        PageResponse<ReportResponse> response = reportService.getReports(status, pageable);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/reports/{reportId}")
    public ResponseEntity<ApiResponse<ReportResponse>> getReport(@PathVariable Long reportId) {
        ReportResponse response = reportService.getReport(reportId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/reports/{reportId}/process")
    public ResponseEntity<ApiResponse<ReportResponse>> processReport(
            @PathVariable Long reportId,
            @CurrentUser CustomUserDetails userDetails,
            @Valid @RequestBody ReportProcessRequest request) {
        ReportResponse response = reportService.processReport(reportId, userDetails.getUser(), request);
        return ResponseEntity.ok(ApiResponse.success(response, "신고가 처리되었습니다"));
    }
}
