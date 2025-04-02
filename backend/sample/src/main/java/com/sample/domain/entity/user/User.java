package com.sample.domain.entity.user;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sample.domain.entity.time.DefaultTime;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Builder;
import lombok.Getter;

@DynamicUpdate
@Entity
@Getter
@Table(name = "user")
public class User extends DefaultTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Email
    @Column(nullable = false, length = 255)
    private String email;

    private String imageUrl;

    @Column(nullable = false)
    private Boolean emailVerified = false;

    @JsonIgnore
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Provider provider;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String providerId;

    public User() {}

    @Builder
    public User(String name, String email, String password, Role role, Provider provider, String providerId, String imageUrl) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.imageUrl = imageUrl;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // 비밀번호 업데이트를 위한 메서드 추가
    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }
}
