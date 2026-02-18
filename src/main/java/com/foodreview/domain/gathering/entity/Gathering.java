package com.foodreview.domain.gathering.entity;

import com.foodreview.domain.common.BaseTimeEntity;
import com.foodreview.domain.restaurant.entity.Restaurant;
import com.foodreview.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "gatherings")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Gathering extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 36)
    private String uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 500)
    private String description;

    @Column(name = "target_time", nullable = false)
    private LocalDateTime targetTime;

    @Column(name = "max_participants", nullable = false)
    private Integer maxParticipants;

    @Column(name = "deposit_amount", nullable = false)
    private Integer depositAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private GatheringStatus status = GatheringStatus.RECRUITING;

    @Enumerated(EnumType.STRING)
    @Column(name = "refund_type", nullable = false)
    @Builder.Default
    private RefundType refundType = RefundType.AUTO;

    @Column(name = "chat_room_uuid", length = 36)
    private String chatRoomUuid;

    @OneToMany(mappedBy = "gathering", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<GatheringParticipant> participants = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID().toString();
        }
    }

    public int getCurrentParticipantCount() {
        return (int) participants.stream()
                .filter(p -> p.getDepositStatus() == DepositStatus.DEPOSITED)
                .count();
    }
}
