package com.sample.repository.auth;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sample.domain.entity.user.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {
    Optional<Token> findByUserEmail(String userEmail);
    Optional<Token> findByRefreshToken(String refreshToken);
}
