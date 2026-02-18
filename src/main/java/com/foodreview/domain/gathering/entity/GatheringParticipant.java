package com.foodreview.domain.gathering.entity;

import com.foodreview.domain.common.BaseTimeEntity;
import com.foodreview.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "gathering_participants",
        uniqueConstraints = @UniqueConstraint(columnNames = {"gathering_id", "user_id"}))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class GatheringParticipant extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gathering_id", nullable = false)
    private Gathering gathering;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "deposit_status", nullable = false)
    @Builder.Default
    private DepositStatus depositStatus = DepositStatus.PENDING;

    @Column(name = "imp_uid", length = 100)
    private String impUid;

    @Column(name = "merchant_uid", length = 100)
    private String merchantUid;

    @Column(name = "refund_reason", length = 200)
    private String refundReason;

    public void updateDepositStatus(DepositStatus status) {
        this.depositStatus = status;
    }

    public void setRefundReason(String reason) {
        this.refundReason = reason;
    }
}
