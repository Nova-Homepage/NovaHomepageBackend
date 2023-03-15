package com.board.novaapi.OAuth;

import java.util.Map;

public class GithubOAuth2UserInfo extends OAuth2UserInfo {
    public GithubOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }
    @Override
    public String getId() {
        return ((Integer) attributes.get("id")).toString();
    }

    // 깃허브 이름 과 이메일이 공개가 아닐 경우 반환 값이 없음으로 임의로 not name 및 not email 할당
    @Override
    public String getName() {
        if(attributes.get("name") == null){
            return "not name";
        }else{
            return (String) attributes.get("name");
        }
    }

    @Override
    public String getEmail() {
        if(attributes.get("email") == null){
            return "not email";
        }else{
            return (String) attributes.get("email");
        }
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get("avatar_url");
    }

}
