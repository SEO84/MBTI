package com.sample.config.security.auth.company;

import java.util.Map;
import com.sample.config.security.auth.OAuth2UserInfo;
import com.sample.domain.entity.user.Provider;

public class Naver extends OAuth2UserInfo {
    public Naver(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return response != null ? (String) response.get("id") : null;
    }

    @Override
    public String getName() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return response != null ? (String) response.get("nickname") : null;
    }

    @Override
    public String getEmail() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return response != null ? (String) response.get("email") : null;
    }

    @Override
    public String getImageUrl() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return response != null ? (String) response.get("profile_image") : null;
    }

    @Override
    public String getProvider() {
        return Provider.naver.toString();
    }
}
