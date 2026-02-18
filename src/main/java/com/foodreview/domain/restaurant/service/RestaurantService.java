package com.foodreview.domain.restaurant.service;

import com.foodreview.domain.restaurant.dto.PendingRestaurantResponse;
import com.foodreview.domain.restaurant.entity.Restaurant;
import com.foodreview.domain.restaurant.entity.RestaurantApprovalStatus;
import com.foodreview.domain.restaurant.repository.RestaurantRepository;
import com.foodreview.global.common.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    // 승인 대기 음식점 목록 조회
    public PageResponse<PendingRestaurantResponse> getPendingRestaurants(Pageable pageable) {
        Page<Restaurant> restaurants = restaurantRepository.findByApprovalStatus(RestaurantApprovalStatus.PENDING, pageable);
        List<PendingRestaurantResponse> content = restaurants.getContent().stream()
                .map(PendingRestaurantResponse::from)
                .toList();
        return PageResponse.from(restaurants, content);
    }

    // 승인 대기 음식점 수
    public long getPendingRestaurantCount() {
        return restaurantRepository.countByApprovalStatus(RestaurantApprovalStatus.PENDING);
    }

    // 음식점 상세 조회
    public PendingRestaurantResponse getRestaurant(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "음식점을 찾을 수 없습니다"));
        return PendingRestaurantResponse.from(restaurant);
    }

    // 음식점 승인
    @Transactional
    public PendingRestaurantResponse approveRestaurant(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "음식점을 찾을 수 없습니다"));

        restaurant.approve();
        log.info("음식점 승인 완료. restaurantId: {}, name: {}", restaurantId, restaurant.getName());

        return PendingRestaurantResponse.from(restaurant);
    }

    // 음식점 거부
    @Transactional
    public PendingRestaurantResponse rejectRestaurant(Long restaurantId, String reason) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "음식점을 찾을 수 없습니다"));

        restaurant.reject(reason);
        log.info("음식점 거부 완료. restaurantId: {}, name: {}, reason: {}", restaurantId, restaurant.getName(), reason);

        return PendingRestaurantResponse.from(restaurant);
    }
}
