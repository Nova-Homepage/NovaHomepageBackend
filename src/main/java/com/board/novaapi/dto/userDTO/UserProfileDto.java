package com.board.novaapi.dto.userDTO;

import com.board.novaapi.entity.user.UserProfile;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserProfileDto {

    private String studentId;

    private String profileComment;

    private String blogInfo;

    private String phone;

    private String profileImageUrl;

    private String username;

    public UserProfileDto(UserProfile profile) {
        this.studentId = profile.getStudentId();
        this.profileComment = profile.getProfileComment();
        this.blogInfo = profile.getBlogInfo();
        this.phone = profile.getPhone();
        this.profileImageUrl = profile.getUser().getProfileImageUrl();
        this.username = profile.getUser().getUsername();
    }

}
