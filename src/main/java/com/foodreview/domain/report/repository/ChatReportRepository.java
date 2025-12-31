package com.foodreview.domain.report.repository;

import com.foodreview.domain.report.entity.ChatReport;
import com.foodreview.domain.report.entity.ReportStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatReportRepository extends JpaRepository<ChatReport, Long> {

    Page<ChatReport> findByStatus(ReportStatus status, Pageable pageable);

    @Query("SELECT cr FROM ChatReport cr " +
           "JOIN FETCH cr.reporter " +
           "JOIN FETCH cr.reportedUser " +
           "JOIN FETCH cr.chatRoom " +
           "WHERE (:status IS NULL OR cr.status = :status)")
    Page<ChatReport> findAllWithDetails(@Param("status") ReportStatus status, Pageable pageable);

    long countByStatus(ReportStatus status);
}
