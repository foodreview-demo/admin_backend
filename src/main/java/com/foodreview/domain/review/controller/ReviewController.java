package com.foodreview.domain.review.controller;

import com.foodreview.domain.review.dto.ReceiptReviewResponse;
import com.foodreview.domain.review.service.ReviewService;
import com.foodreview.global.common.ApiResponse;
import com.foodreview.global.common.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Admin 리뷰 관리 컨트롤러
 */
@RestController
@RequestMapping("/api/admin/reviews")
@RequiredArgsConstructor
@Tag(name = "Admin Reviews", description = "리뷰 관리 API")
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "영수증 검토 대기 리뷰 목록")
    @GetMapping("/receipts/pending")
    public ResponseEntity<ApiResponse<PageResponse<ReceiptReviewResponse>>> getPendingReceiptReviews(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(reviewService.getPendingReceiptReviews(pageable)));
    }

    @Operation(summary = "영수증 수동 승인")
    @PostMapping("/{reviewId}/receipt/approve")
    public ResponseEntity<ApiResponse<Void>> approveReceipt(@PathVariable Long reviewId) {
        reviewService.approveReceipt(reviewId);
        return ResponseEntity.ok(ApiResponse.success(null, "영수증이 승인되었습니다"));
    }

    @Operation(summary = "영수증 수동 거부")
    @PostMapping("/{reviewId}/receipt/reject")
    public ResponseEntity<ApiResponse<Void>> rejectReceipt(@PathVariable Long reviewId) {
        reviewService.rejectReceipt(reviewId);
        return ResponseEntity.ok(ApiResponse.success(null, "영수증이 거부되었습니다"));
    }
}
