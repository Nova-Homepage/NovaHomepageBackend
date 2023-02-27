package com.board.novaapi.service;

import com.board.novaapi.OAuth.Entity.RoleType;
import com.board.novaapi.entity.user.User;
import com.board.novaapi.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    /***
     *모든 멤버의 요약정보 리스트 반환을 위해 사용하는 함수
     * @return 모든멤버의 요약정보 리스트 반환.
     *
     */
    @Transactional(readOnly = true)
    public Page<User> getAllMemberInfo(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    /***
     * 멤버 권한 변경을 위해 사용하는 함수
     * @param userId 멤버 아이디
     * @param roleType 멤버 권한
     */
    @Transactional
    public void UpdateMemberRoleType(String userId, RoleType roleType){
        userRepository.UpdateRoleTypeByuserId(userId, RoleType.of(roleType.getCode()));
    }
}

