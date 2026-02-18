package com.foodreview.domain.gathering.dto;

import com.foodreview.domain.gathering.entity.Gathering;
import com.foodreview.domain.gathering.entity.GatheringStatus;
import com.foodreview.domain.gathering.entity.RefundType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GatheringAdminResponse {
    private Long id;
    private String uuid;
    private String title;
    private String description;
    private String status;
    private String statusDisplay;
    private String refundType;
    private String refundTypeDisplay;
    private LocalDateTime targetTime;
    private Integer maxParticipants;
    private Integer currentParticipants;
    private Integer depositAmount;
    private String chatRoomUuid;
    private RestaurantInfo restaurant;
    private UserInfo creator;
    private LocalDateTime createdAt;

    @Getter
    @Builder
    public static class RestaurantInfo {
        private Long id;
        private String name;
        private String address;
        private String category;
    }

    @Getter
    @Builder
    public static class UserInfo {
        private Long id;
        private String name;
        private String email;
        private String avatar;
    }

    public static GatheringAdminResponse from(Gathering gathering) {
        return GatheringAdminResponse.builder()
                .id(gathering.getId())
                .uuid(gathering.getUuid())
                .title(gathering.getTitle())
                .description(gathering.getDescription())
                .status(gathering.getStatus().name())
                .statusDisplay(gathering.getStatus().getDisplayName())
                .refundType(gathering.getRefundType().name())
                .refundTypeDisplay(gathering.getRefundType().getDisplayName())
                .targetTime(gathering.getTargetTime())
                .maxParticipants(gathering.getMaxParticipants())
                .currentParticipants(gathering.getCurrentParticipantCount())
                .depositAmount(gathering.getDepositAmount())
                .chatRoomUuid(gathering.getChatRoomUuid())
                .restaurant(RestaurantInfo.builder()
                        .id(gathering.getRestaurant().getId())
                        .name(gathering.getRestaurant().getName())
                        .address(gathering.getRestaurant().getAddress())
                        .category(gathering.getRestaurant().getCategory().getDisplayName())
                        .build())
                .creator(UserInfo.builder()
                        .id(gathering.getCreator().getId())
                        .name(gathering.getCreator().getName())
                        .email(gathering.getCreator().getEmail())
                        .avatar(gathering.getCreator().getAvatar())
                        .build())
                .createdAt(gathering.getCreatedAt())
                .build();
    }
}
