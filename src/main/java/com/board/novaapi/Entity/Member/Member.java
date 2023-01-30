package com.board.novaapi.Entity.Member;

import com.board.novaapi.Entity.common.EntityDate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.crypto.password.PasswordEncoder;

@Table(name = "member")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends EntityDate {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "studentId")
    private String studentId;

    @Column(nullable = false,length = 50, unique = true)
    private String email;

    @Column(nullable = false, length = 10)
    private String name;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("READY")
    private Role role;

    public Member(String studentId, String email, String name, String password) {
        this.studentId = studentId;
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    // 기본적으로 entity에 setter 연산은 들어가서는 안된다. 따라서 추후에 memberRepository 로 배서 쿼리 작성할 듯.


}
