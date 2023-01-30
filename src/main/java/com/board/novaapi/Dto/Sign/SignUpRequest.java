package com.board.novaapi.Dto.Sign;

import com.board.novaapi.Entity.Member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@AllArgsConstructor
public class SignUpRequest {
    private String studentId;
    private String password;
    private String email;
    private String name;

    public static Member toEntity(SignUpRequest req, PasswordEncoder passwordEncoder) {
        return new Member(req.studentId,req.email, passwordEncoder.encode(req.password), req.name);
    }
}
