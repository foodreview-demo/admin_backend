package com.foodreview.domain.user.dto;

import com.foodreview.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private String email;
    private String name;
    private String avatar;
    private String role;
    private String region;
    private Integer tasteScore;

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .avatar(user.getAvatar())
                .role(user.getRole().name())
                .region(user.getRegion())
                .tasteScore(user.getTasteScore())
                .build();
    }
}
