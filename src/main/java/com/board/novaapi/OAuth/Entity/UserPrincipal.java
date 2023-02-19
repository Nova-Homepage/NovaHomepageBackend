package com.board.novaapi.OAuth.Entity;

import com.board.novaapi.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class UserPrincipal implements OAuth2User, UserDetails, OidcUser {
    private final String userId;
    private final ProviderType providerType;
    private final RoleType roleType;
    private final Collection<GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return userId;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getClaims() {
        return null;
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public OidcIdToken getIdToken() {
        return null;
    }

    //처음 생성되는 경우 무조건 roletype을 guest로 설정한다.
    public static UserPrincipal create(User user) {
        return new UserPrincipal(
                user.getUserId(),
                user.getProviderType(),
                RoleType.GUEST,
                Collections.singletonList(new SimpleGrantedAuthority(RoleType.GUEST.getCode()))
        );
    }

    // UserPrincipal create 부분에서 회우너에 대한 토큰 생성을 하는데 영향을 주고 있다
    // 따라서 해당 부분에서 기존에 등록된 회원의 경우 권한이 있는지 확인해야 하고 그 권한과 관련된 작업을 수행 해야함으로
    // 전달받은 roletype 에 맞게 생성하도록 한다.
    public static UserPrincipal create(User user, RoleType roleType) {
        return new UserPrincipal(
                user.getUserId(),
                user.getProviderType(),
                roleType,
                Collections.singletonList(new SimpleGrantedAuthority(roleType.getCode()))
        );
    }

    // roletype의 경우 roletype 에 맞게 들어와야 하는데, 우리가 주는 값은 string 으로 들어온다 따라서 string 에 해당하는 값을
    // 타입에 맞게 변경해 주는 작업이 필요하다.
    private static RoleType toRoleType(String stringRoleType){
        if (stringRoleType.equals("ADMIN")){
            return RoleType.ADMIN;
        }
        else if(stringRoleType.equals("USER")){
            return RoleType.USER;
        }
        else if(stringRoleType.equals("GUEST")){
            return RoleType.GUEST;
        }else{
            System.out.println("잘못된 RoleType 주입. guest로 초기화");
            return RoleType.GUEST;
        }
    }

    public static UserPrincipal create(User user, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = create(user);
        userPrincipal.setAttributes(attributes);

        return userPrincipal;
    }

    public static UserPrincipal create(User user,String roleType, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = create(user,toRoleType(roleType));
        System.out.println("해당 함수의 set attribute 값 확인");
        userPrincipal.setAttributes(attributes);
        System.out.println(userPrincipal.getAuthorities());
        return userPrincipal;
    }
}
