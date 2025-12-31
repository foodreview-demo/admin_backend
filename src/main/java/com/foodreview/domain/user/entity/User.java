package com.foodreview.domain.user.entity;

import com.foodreview.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(length = 500)
    private String avatar;

    @Column(nullable = false, length = 50)
    private String region;

    @Column(length = 50)
    private String district;

    @Column(length = 50)
    private String neighborhood;

    @Column(name = "taste_score", nullable = false)
    @Builder.Default
    private Integer tasteScore = 0;

    @Column(name = "review_count", nullable = false)
    @Builder.Default
    private Integer reviewCount = 0;

    @Column(name = "received_sympathy_count", nullable = false)
    @Builder.Default
    private Integer receivedSympathyCount = 0;

    @ElementCollection
    @CollectionTable(name = "user_favorite_categories", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "category")
    @Builder.Default
    private List<String> favoriteCategories = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private Role role = Role.USER;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider")
    private AuthProvider provider;

    @Column(name = "provider_id")
    private String providerId;
}
