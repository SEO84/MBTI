package com.sample.service.user;

import java.util.Optional;

import com.sample.advice.assertThat.DefaultAssert;
import com.sample.config.security.token.UserPrincipal;
import com.sample.domain.entity.user.User;
import com.sample.payload.response.ApiResponse;
import com.sample.repository.user.UserRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public ResponseEntity<?> readByUser(UserPrincipal userPrincipal) {
        Optional<User> user = userRepository.findById(userPrincipal.getId());
        // assertOptionalPresent 대신 isOptionalPresent 사용
        DefaultAssert.isOptionalPresent(user); // 수정된 코드
        ApiResponse apiResponse = ApiResponse.builder()
                .check(true)
                .information(user.get())
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
