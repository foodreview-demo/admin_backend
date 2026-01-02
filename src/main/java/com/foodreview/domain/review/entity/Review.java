package com.foodreview.domain.review.entity;

import com.foodreview.domain.common.BaseTimeEntity;
import com.foodreview.domain.restaurant.entity.Restaurant;
import com.foodreview.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reviews",
        indexes = {
            @Index(name = "idx_review_restaurant_created", columnList = "restaurant_id, created_at DESC"),
            @Index(name = "idx_review_user_created", columnList = "user_id, created_at DESC")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(nullable = false, precision = 2, scale = 1)
    private BigDecimal rating;

    @Column(name = "taste_rating", precision = 2, scale = 1)
    private BigDecimal tasteRating;

    @Column(name = "price_rating", precision = 2, scale = 1)
    private BigDecimal priceRating;

    @Column(name = "atmosphere_rating", precision = 2, scale = 1)
    private BigDecimal atmosphereRating;

    @Column(name = "service_rating", precision = 2, scale = 1)
    private BigDecimal serviceRating;

    @ElementCollection
    @CollectionTable(name = "review_images", joinColumns = @JoinColumn(name = "review_id"))
    @Column(name = "image_url", length = 500)
    @Builder.Default
    private List<String> images = new ArrayList<>();

    @Column(length = 100)
    private String menu;

    @Column(length = 50)
    private String price;

    @Column(name = "visit_date")
    private LocalDate visitDate;

    @Column(name = "sympathy_count", nullable = false)
    @Builder.Default
    private Integer sympathyCount = 0;

    @Column(name = "is_first_review", nullable = false)
    @Builder.Default
    private Boolean isFirstReview = false;

    @Column(name = "receipt_image_url", length = 500)
    private String receiptImageUrl;

    // 영수증 검증 상태
    @Enumerated(EnumType.STRING)
    @Column(name = "receipt_verification_status", length = 20)
    @Builder.Default
    private ReceiptVerificationStatus receiptVerificationStatus = ReceiptVerificationStatus.NONE;

    // 영수증 검증 점수 (0 ~ 100)
    @Column(name = "receipt_verification_score")
    private Integer receiptVerificationScore;

    // OCR 추출 텍스트 (검토용)
    @Column(name = "receipt_ocr_text", length = 2000)
    private String receiptOcrText;

    // Admin 수동 승인
    public void approveReceiptManually() {
        this.receiptVerificationStatus = ReceiptVerificationStatus.MANUALLY_APPROVED;
    }

    // Admin 수동 거부
    public void rejectReceiptManually() {
        this.receiptVerificationStatus = ReceiptVerificationStatus.MANUALLY_REJECTED;
    }

    // 영수증이 인증된 상태인지 확인
    public boolean isReceiptVerified() {
        return this.receiptVerificationStatus == ReceiptVerificationStatus.VERIFIED
                || this.receiptVerificationStatus == ReceiptVerificationStatus.MANUALLY_APPROVED;
    }
}
