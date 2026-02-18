package com.foodreview.domain.gathering.repository;

import com.foodreview.domain.gathering.entity.Gathering;
import com.foodreview.domain.gathering.entity.GatheringStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GatheringRepository extends JpaRepository<Gathering, Long> {

    Optional<Gathering> findByUuid(String uuid);

    Page<Gathering> findByStatus(GatheringStatus status, Pageable pageable);

    long countByStatus(GatheringStatus status);

    @Query("SELECT COUNT(p) FROM GatheringParticipant p WHERE p.depositStatus = 'REFUND_FAILED'")
    long countFailedRefunds();

    @Query("SELECT g FROM Gathering g JOIN FETCH g.restaurant JOIN FETCH g.creator " +
           "WHERE g.status = :status ORDER BY g.createdAt DESC")
    Page<Gathering> findByStatusWithDetails(@Param("status") GatheringStatus status, Pageable pageable);
}
