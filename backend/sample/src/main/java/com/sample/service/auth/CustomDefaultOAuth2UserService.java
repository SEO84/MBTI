package com.sample.service.auth;

import java.util.Optional;

import com.sample.advice.assertThat.DefaultAssert;
import com.sample.config.security.auth.OAuth2UserInfo;
import com.sample.config.security.auth.OAuth2UserInfoFactory;
import com.sample.config.security.token.UserPrincipal;
import com.sample.domain.entity.user.Provider;
import com.sample.domain.entity.user.Role;
import com.sample.domain.entity.user.User;
import com.sample.repository.user.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomDefaultOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (Exception e) {
            DefaultAssert.isAuthentication(e.getMessage());
            return null;
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
                oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        DefaultAssert.isAuthentication(!oAuth2UserInfo.getEmail().isEmpty());

        Optional<User> userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail());
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            DefaultAssert.isAuthentication(user.getProvider().equals(Provider.valueOf(
                    oAuth2UserRequest.getClientRegistration().getRegistrationId())));
            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }
        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }

    private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        // 네이버에서 이름이 null 또는 빈 문자열일 경우 이메일 또는 기본값을 사용하도록 대체
        String name = oAuth2UserInfo.getName();
        if (name == null || name.trim().isEmpty()) {
            // 또는 "Unknown User"와 같은 기본 문자열로 지정할 수 있습니다.
            name = oAuth2UserInfo.getEmail();
        }
        User user = User.builder()
                .provider(Provider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))
                .providerId(oAuth2UserInfo.getId())
                .name(name)
                .email(oAuth2UserInfo.getEmail())
                .imageUrl(oAuth2UserInfo.getImageUrl())
                .role(Role.USER)
                .build();
        return userRepository.save(user);
    }


    private User updateExistingUser(User user, OAuth2UserInfo oAuth2UserInfo) {
        user.updateName(oAuth2UserInfo.getName());
        user.updateImageUrl(oAuth2UserInfo.getImageUrl());
        return userRepository.save(user);
    }
}
