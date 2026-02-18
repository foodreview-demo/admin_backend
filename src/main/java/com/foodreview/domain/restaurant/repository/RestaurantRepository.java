package com.foodreview.domain.restaurant.repository;

import com.foodreview.domain.restaurant.entity.Restaurant;
import com.foodreview.domain.restaurant.entity.RestaurantApprovalStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    // 승인 상태별 음식점 조회
    Page<Restaurant> findByApprovalStatus(RestaurantApprovalStatus status, Pageable pageable);

    // 승인 대기 음식점 수
    long countByApprovalStatus(RestaurantApprovalStatus status);
}
