package com.board.novaapi.entity.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USER_PROFILE")
public class UserProfile {
    @Id
    @Column(name = "USER_PROFILE_SEQ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userProfileSeq;

    @Column(name = "STUDENT_ID")
    private String studentId;

    @Column(name = "PROFILE_COMMENT")
    private String profileComment;

    @Column(name = "BLOG_INFO")
    private String blogInfo;

    @Column(name = "PHONE")
    private String phone;
    /***
     * User 엔터티와 one to one 단방향
     * User 객체를 통해서만 사용자 프로필에 접근 가능
     */
    @OneToOne(mappedBy = "userProfile")
    private User user;
    public UserProfile(String studentId, String profileComment, String blogInfo, String phone) {
        this.studentId = studentId;
        this.profileComment = profileComment;
        this.blogInfo = blogInfo;
        this.phone = phone;
    }
}
