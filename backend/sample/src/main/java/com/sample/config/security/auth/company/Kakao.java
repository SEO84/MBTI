package com.sample.config.security.auth.company;

import java.util.Map;
import com.sample.config.security.auth.OAuth2UserInfo;
import com.sample.domain.entity.user.Provider;

public class Kakao extends OAuth2UserInfo {
    public Kakao(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getName() {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        return properties != null ? (String) properties.get("nickname") : null;
    }

    @Override
    public String getEmail() {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("kakao_account");
        return properties != null ? (String) properties.get("email") : null;
    }

    @Override
    public String getImageUrl() {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        return properties != null ? (String) properties.get("thumbnail_image") : null;
    }

    @Override
    public String getProvider() {
        return Provider.kakao.toString();
    }
}
