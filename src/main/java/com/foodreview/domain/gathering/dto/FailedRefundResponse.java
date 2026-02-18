package com.foodreview.domain.gathering.dto;

import com.foodreview.domain.gathering.entity.GatheringParticipant;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class FailedRefundResponse {
    private Long id;
    private Long gatheringId;
    private String gatheringUuid;
    private String gatheringTitle;
    private String restaurantName;
    private Long userId;
    private String userName;
    private String userEmail;
    private Integer depositAmount;
    private String impUid;
    private String merchantUid;
    private String refundReason;
    private LocalDateTime createdAt;
    private LocalDateTime gatheringTargetTime;

    public static FailedRefundResponse from(GatheringParticipant participant) {
        return FailedRefundResponse.builder()
                .id(participant.getId())
                .gatheringId(participant.getGathering().getId())
                .gatheringUuid(participant.getGathering().getUuid())
                .gatheringTitle(participant.getGathering().getTitle())
                .restaurantName(participant.getGathering().getRestaurant().getName())
                .userId(participant.getUser().getId())
                .userName(participant.getUser().getName())
                .userEmail(participant.getUser().getEmail())
                .depositAmount(participant.getGathering().getDepositAmount())
                .impUid(participant.getImpUid())
                .merchantUid(participant.getMerchantUid())
                .refundReason(participant.getRefundReason())
                .createdAt(participant.getCreatedAt())
                .gatheringTargetTime(participant.getGathering().getTargetTime())
                .build();
    }
}
