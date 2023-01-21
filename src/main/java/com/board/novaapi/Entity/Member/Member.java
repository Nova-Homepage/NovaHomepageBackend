package com.board.novaapi.Entity.Member;

import com.board.novaapi.Entity.common.EntityDate;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends EntityDate {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private String studentId;

    @Column(nullable = false,length = 50, unique = true)
    private String email;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @ColumnDefault("READY")
    private Role role;

    public Member(String studentId, String email, String name, String password, Role role) {
        this.studentId = studentId;
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    // 추후 관리자 승인으로 권한을 변경할 수 있게 하기 위해 설정.
    public void updateRole(Role role){
        this.role = role;
    }
}
