package com.sample.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class AuthResponse {
    @Schema(type = "string", example = "accessTokenExample", description = "access token")
    private String accessToken;

    @Schema(type = "string", example = "refreshTokenExample", description = "refresh token")
    private String refreshToken;

    @Schema(type = "string", example = "Bearer", description = "토큰 형식")
    private String tokenType = "Bearer";

    public AuthResponse() {}

    @Builder
    public AuthResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
