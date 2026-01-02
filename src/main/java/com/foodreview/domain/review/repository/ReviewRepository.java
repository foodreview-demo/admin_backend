package com.foodreview.domain.review.repository;

import com.foodreview.domain.review.entity.ReceiptVerificationStatus;
import com.foodreview.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 영수증 검증 상태별 리뷰 조회
    @Query("SELECT r FROM Review r WHERE r.receiptVerificationStatus = :status ORDER BY r.createdAt DESC")
    Page<Review> findByReceiptVerificationStatus(
            @Param("status") ReceiptVerificationStatus status,
            Pageable pageable);

    // 영수증 검증 대기 리뷰 수
    @Query("SELECT COUNT(r) FROM Review r WHERE r.receiptVerificationStatus = :status")
    long countByReceiptVerificationStatus(@Param("status") ReceiptVerificationStatus status);
}
