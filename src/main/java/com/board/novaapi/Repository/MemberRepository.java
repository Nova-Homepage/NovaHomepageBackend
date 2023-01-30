package com.board.novaapi.Repository;


import com.board.novaapi.Entity.Member.Member;
import com.board.novaapi.Entity.Member.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    Optional<Member> findByStudentId(String studentId);

    boolean existsByStudentId(String studentId);

}
