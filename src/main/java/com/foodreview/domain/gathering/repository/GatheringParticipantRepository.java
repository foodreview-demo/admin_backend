package com.foodreview.domain.gathering.repository;

import com.foodreview.domain.gathering.entity.DepositStatus;
import com.foodreview.domain.gathering.entity.GatheringParticipant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GatheringParticipantRepository extends JpaRepository<GatheringParticipant, Long> {

    @Query("SELECT p FROM GatheringParticipant p " +
           "JOIN FETCH p.gathering g " +
           "JOIN FETCH p.user u " +
           "JOIN FETCH g.restaurant " +
           "WHERE p.depositStatus = :status " +
           "ORDER BY p.createdAt DESC")
    Page<GatheringParticipant> findByDepositStatusWithDetails(DepositStatus status, Pageable pageable);

    long countByDepositStatus(DepositStatus status);
}
