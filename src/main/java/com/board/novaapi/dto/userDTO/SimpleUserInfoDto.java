package com.board.novaapi.dto.userDTO;

import com.board.novaapi.OAuth.Entity.RoleType;
import com.board.novaapi.entity.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SimpleUserInfoDto {

    private String userId;
    private String username;
    private RoleType roleType;

    public SimpleUserInfoDto(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.roleType = user.getRoleType();
    }
}
