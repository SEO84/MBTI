package com.sample.config.security.handler;

import com.sample.advice.assertThat.DefaultAssert;
import com.sample.config.security.OAuth2Config;
import com.sample.config.security.util.CustomCookie;
import com.sample.domain.entity.user.Token;
import com.sample.domain.mapping.TokenMapping;
import com.sample.repository.auth.CustomAuthorizationRequestRepository;
import com.sample.repository.auth.TokenRepository;
import com.sample.service.auth.CustomTokenProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;
import static com.sample.repository.auth.CustomAuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@RequiredArgsConstructor
@Component
public class CustomSimpleUrlAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final CustomTokenProviderService customTokenProviderService;
    private final OAuth2Config oAuth2Config;
    private final TokenRepository tokenRepository;
    private final CustomAuthorizationRequestRepository customAuthorizationRequestRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        // 응답이 이미 커밋되지 않았는지 확인 (인증 상태 검증)
        DefaultAssert.assertAuthentication(!response.isCommitted());

        // 리디렉션할 URL 결정
        String targetUrl = determineTargetUrl(request, response, authentication);

        // 인증 관련 세션 정보와 쿠키 삭제
        clearAuthenticationAttributes(request, response);

        // 클라이언트로 리디렉션
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) {

        // 쿠키에서 redirect_uri 값을 가져옴
        Optional<String> redirectUri = CustomCookie.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);
        // redirectUri가 존재하는 경우, 허용된 URI인지 체크
        DefaultAssert.assertAuthentication(!(redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())));

        // redirectUri가 없으면 기본 URL 사용
        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        // 토큰 생성 (Authentication 객체를 그대로 전달)
        TokenMapping tokenMapping = customTokenProviderService.createToken(authentication);

        // 생성된 refreshToken을 DB에 저장 (Token 엔티티 사용)
        Token token = Token.builder()
                .userEmail(tokenMapping.getUserEmail())
                .refreshToken(tokenMapping.getRefreshToken())
                .build();
        tokenRepository.save(token);

        // accessToken을 쿼리 파라미터로 포함하여 리디렉션 URL 생성
        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", tokenMapping.getAccessToken())
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request,
                                                 HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        customAuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);
        return oAuth2Config.getOauth2().getAuthorizedRedirectUris()
                .stream()
                .anyMatch(authorizedRedirectUri -> {
                    URI authorizedURI = URI.create(authorizedRedirectUri);
                    return authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                            && authorizedURI.getPort() == clientRedirectUri.getPort();
                });
    }
}
