package com.foodreview.domain.restaurant.entity;

import com.foodreview.domain.common.BaseTimeEntity;
import com.foodreview.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "restaurants",
        indexes = {
            @Index(name = "idx_restaurant_region_category", columnList = "region, category"),
            @Index(name = "idx_restaurant_category", columnList = "category")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Restaurant extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(nullable = false, length = 200)
    private String address;

    @Column(nullable = false, length = 50)
    private String region;

    @Column(length = 50)
    private String district;

    @Column(length = 50)
    private String neighborhood;

    @Column(length = 500)
    private String thumbnail;

    @Column(name = "average_rating", precision = 2, scale = 1)
    @Builder.Default
    private BigDecimal averageRating = BigDecimal.ZERO;

    @Column(name = "average_taste_rating", precision = 2, scale = 1)
    @Builder.Default
    private BigDecimal averageTasteRating = BigDecimal.ZERO;

    @Column(name = "average_price_rating", precision = 2, scale = 1)
    @Builder.Default
    private BigDecimal averagePriceRating = BigDecimal.ZERO;

    @Column(name = "average_atmosphere_rating", precision = 2, scale = 1)
    @Builder.Default
    private BigDecimal averageAtmosphereRating = BigDecimal.ZERO;

    @Column(name = "average_service_rating", precision = 2, scale = 1)
    @Builder.Default
    private BigDecimal averageServiceRating = BigDecimal.ZERO;

    @Column(name = "taste_rating_count", nullable = false)
    @Builder.Default
    private Integer tasteRatingCount = 0;

    @Column(name = "price_rating_count", nullable = false)
    @Builder.Default
    private Integer priceRatingCount = 0;

    @Column(name = "atmosphere_rating_count", nullable = false)
    @Builder.Default
    private Integer atmosphereRatingCount = 0;

    @Column(name = "service_rating_count", nullable = false)
    @Builder.Default
    private Integer serviceRatingCount = 0;

    @Column(name = "review_count", nullable = false)
    @Builder.Default
    private Integer reviewCount = 0;

    @Column(name = "price_range", length = 50)
    private String priceRange;

    @Column(length = 20)
    private String phone;

    @Column(name = "business_hours", length = 200)
    private String businessHours;

    @Column(name = "kakao_place_id", unique = true)
    private String kakaoPlaceId;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    // 수동 등록 관련 필드
    @Column(name = "is_manual_registration")
    @Builder.Default
    private Boolean isManualRegistration = false;

    @Column(name = "signboard_image_url", length = 500)
    private String signboardImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "approval_status")
    @Builder.Default
    private RestaurantApprovalStatus approvalStatus = RestaurantApprovalStatus.APPROVED;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registered_by_id")
    private User registeredBy;

    @Column(name = "rejection_reason", length = 500)
    private String rejectionReason;

    // 승인 상태 변경 메서드
    public void approve() {
        this.approvalStatus = RestaurantApprovalStatus.APPROVED;
        this.rejectionReason = null;
    }

    public void reject(String reason) {
        this.approvalStatus = RestaurantApprovalStatus.REJECTED;
        this.rejectionReason = reason;
    }

    public enum Category {
        KOREAN("한식"),
        JAPANESE("일식"),
        CHINESE("중식"),
        WESTERN("양식"),
        CAFE("카페"),
        BAKERY("베이커리"),
        SNACK("분식");

        private final String displayName;

        Category(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}
