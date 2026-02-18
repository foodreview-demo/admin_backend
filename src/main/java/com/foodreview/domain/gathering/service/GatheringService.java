package com.foodreview.domain.gathering.service;

import com.foodreview.domain.gathering.dto.FailedRefundResponse;
import com.foodreview.domain.gathering.dto.GatheringAdminResponse;
import com.foodreview.domain.gathering.entity.DepositStatus;
import com.foodreview.domain.gathering.entity.Gathering;
import com.foodreview.domain.gathering.entity.GatheringParticipant;
import com.foodreview.domain.gathering.entity.GatheringStatus;
import com.foodreview.domain.gathering.repository.GatheringParticipantRepository;
import com.foodreview.domain.gathering.repository.GatheringRepository;
import com.foodreview.global.common.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GatheringService {

    private final GatheringRepository gatheringRepository;
    private final GatheringParticipantRepository participantRepository;

    public long getActiveGatheringCount() {
        return gatheringRepository.countByStatus(GatheringStatus.RECRUITING) +
               gatheringRepository.countByStatus(GatheringStatus.CONFIRMED) +
               gatheringRepository.countByStatus(GatheringStatus.IN_PROGRESS);
    }

    public long getFailedRefundCount() {
        return participantRepository.countByDepositStatus(DepositStatus.REFUND_FAILED);
    }

    public PageResponse<GatheringAdminResponse> getGatherings(GatheringStatus status, Pageable pageable) {
        Page<Gathering> page;
        if (status != null) {
            page = gatheringRepository.findByStatusWithDetails(status, pageable);
        } else {
            page = gatheringRepository.findAll(pageable);
        }

        return PageResponse.from(page.map(GatheringAdminResponse::from));
    }

    public PageResponse<FailedRefundResponse> getFailedRefunds(Pageable pageable) {
        Page<GatheringParticipant> page = participantRepository
                .findByDepositStatusWithDetails(DepositStatus.REFUND_FAILED, pageable);

        return PageResponse.from(page.map(FailedRefundResponse::from));
    }

    @Transactional
    public FailedRefundResponse markRefundCompleted(Long participantId) {
        GatheringParticipant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new IllegalArgumentException("참여자를 찾을 수 없습니다: " + participantId));

        if (participant.getDepositStatus() != DepositStatus.REFUND_FAILED) {
            throw new IllegalStateException("환금 실패 상태가 아닙니다");
        }

        participant.updateDepositStatus(DepositStatus.REFUNDED);
        participant.setRefundReason("관리자 수동 환금 완료");

        return FailedRefundResponse.from(participant);
    }
}
