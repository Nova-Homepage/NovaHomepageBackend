package com.board.novaapi.OAuth.service;

import com.board.novaapi.OAuth.Entity.ProviderType;
import com.board.novaapi.OAuth.Entity.RoleType;
import com.board.novaapi.OAuth.Entity.UserPrincipal;
import com.board.novaapi.OAuth.Exceptions.OAuthProviderMissMatchException;
import com.board.novaapi.OAuth.OAuth2UserInfo;
import com.board.novaapi.OAuth.OAuth2UserInfoFactory;
import com.board.novaapi.entity.user.User;
import com.board.novaapi.entity.user.UserProfile;
import com.board.novaapi.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);

        try {
            return this.process(userRequest, user);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user) {
        ProviderType providerType = ProviderType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, user.getAttributes());
        User savedUser = userRepository.findByUserId(userInfo.getId());

        if (savedUser != null) {

            if (providerType != savedUser.getProviderType()) {
                throw new OAuthProviderMissMatchException(
                        "이미 등록된 회원입니다. " + providerType +
                                savedUser.getProviderType() + " 계정으로 로그인 해주세요 "
                );
            }
            /**
             * 기존에 등록된 회원의 경우 프로필 이미지와 이름만 갱신
             */
            System.out.println("update 진행중");
            updateUser(savedUser, userInfo);
        } else {

            System.out.println("등록된 회원이 아닙니다.");
            savedUser = createUser(userInfo, providerType);
        }

        return UserPrincipal.create(savedUser, userRepository.getRoleTypeByUserId(userInfo.getId()), user.getAttributes());
    }

    private User createUser(OAuth2UserInfo userInfo, ProviderType providerType) {
        LocalDateTime now = LocalDateTime.now();

        UserProfile profile = new UserProfile(
                null,
                null,
                null,
                null
        );
        System.out.println("프로필");

        System.out.println(profile);
        User user = new User(
                userInfo.getId(),
                userInfo.getName(),
                userInfo.getEmail(),
                "Y",
                userInfo.getImageUrl(),
                providerType,
                RoleType.GUEST,
                now,
                now
        );
        user.setUserProfile(profile);
        return userRepository.save(user);
    }

    //user update 를 통해서 update 뿐 아니라 최근 접속일 도 확인 할 수 있음
    // 추후 추가 사항
    private User updateUser(User user, OAuth2UserInfo userInfo) {
        if (userInfo.getName() != null && !user.getUsername().equals(userInfo.getName())) {
            user.setUsername(userInfo.getName());
        }

        if (userInfo.getImageUrl() != null && !user.getProfileImageUrl().equals(userInfo.getImageUrl())) {
            user.setProfileImageUrl(userInfo.getImageUrl());
        }

        return user;
    }
}
