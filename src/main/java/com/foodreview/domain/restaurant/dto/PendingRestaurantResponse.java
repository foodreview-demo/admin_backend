package com.foodreview.domain.restaurant.dto;

import com.foodreview.domain.restaurant.entity.Restaurant;
import com.foodreview.domain.restaurant.entity.RestaurantApprovalStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PendingRestaurantResponse {
    private Long id;
    private String name;
    private String category;
    private String categoryDisplay;
    private String address;
    private String region;
    private String district;
    private String neighborhood;
    private String signboardImageUrl;
    private RestaurantApprovalStatus approvalStatus;
    private String approvalStatusDisplay;
    private Double latitude;
    private Double longitude;
    private LocalDateTime createdAt;
    // 등록자 정보
    private Long registeredById;
    private String registeredByName;

    public static PendingRestaurantResponse from(Restaurant restaurant) {
        return PendingRestaurantResponse.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .category(restaurant.getCategory().name())
                .categoryDisplay(restaurant.getCategory().getDisplayName())
                .address(restaurant.getAddress())
                .region(restaurant.getRegion())
                .district(restaurant.getDistrict())
                .neighborhood(restaurant.getNeighborhood())
                .signboardImageUrl(restaurant.getSignboardImageUrl())
                .approvalStatus(restaurant.getApprovalStatus())
                .approvalStatusDisplay(restaurant.getApprovalStatus() != null ? restaurant.getApprovalStatus().getDisplayName() : null)
                .latitude(restaurant.getLatitude())
                .longitude(restaurant.getLongitude())
                .createdAt(restaurant.getCreatedAt())
                .registeredById(restaurant.getRegisteredBy() != null ? restaurant.getRegisteredBy().getId() : null)
                .registeredByName(restaurant.getRegisteredBy() != null ? restaurant.getRegisteredBy().getName() : null)
                .build();
    }
}
