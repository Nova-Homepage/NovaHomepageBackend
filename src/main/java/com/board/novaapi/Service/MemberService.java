package com.board.novaapi.Service;

import com.board.novaapi.Dto.Sign.SignUpRequest;
import com.board.novaapi.Repository.MemberRepository;
import com.board.novaapi.Exception.MemberStudentIdAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(SignUpRequest req) {
        validateSignUpInfo(req);
        memberRepository.save(SignUpRequest.toEntity(req, passwordEncoder));
    }
    private void validateSignUpInfo(SignUpRequest req) {
        if(memberRepository.existsByStudentId(req.getStudentId()))

            throw new MemberStudentIdAlreadyExistsException(req.getStudentId());
    }

}
