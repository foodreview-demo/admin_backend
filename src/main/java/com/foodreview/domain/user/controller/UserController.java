package com.foodreview.domain.user.controller;

import com.foodreview.domain.user.dto.UserResponse;
import com.foodreview.global.common.ApiResponse;
import com.foodreview.global.security.CurrentUser;
import com.foodreview.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentUser(@CurrentUser CustomUserDetails userDetails) {
        UserResponse response = UserResponse.from(userDetails.getUser());
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
