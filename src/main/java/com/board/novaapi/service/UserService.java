package com.board.novaapi.service;


import com.board.novaapi.OAuth.Entity.RoleType;
import com.board.novaapi.entity.user.User;
import com.board.novaapi.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUser(String userId) {
        return userRepository.findByUserId(userId);
    }

    public boolean checkUserIdExist(String userId){
        return userRepository.checkUserIdExist(userId);
    }

    // userid, username, roletype 만을 제공 (권한 변경에 필수적인 사항만)
    public List<String> getAllUserNameAndRoleType(){
        return userRepository.getAllSimpleMemberInfo();
    }

    //
    public void UpdateMemberRoleType(String userId, String stringRoleType){
        //string roleType를 변경해주어야 함.
        System.out.println("update 시 주입된 roletype 확인 : " + stringRoleType);
        RoleType roleType;
        if (stringRoleType.equals("ADMIN")){
            roleType = RoleType.ADMIN;
        }
        else if(stringRoleType.equals("USER")){
            roleType = RoleType.USER;
        }
        else if(stringRoleType.equals("GUEST")){
            roleType = RoleType.GUEST;
        }else{
            System.out.println("잘못된 RoleType 주입. error 발생 GUEST 로 초기화");
            roleType = RoleType.GUEST;
        }
        userRepository.UpdateRoleTypeByuserId(userId, roleType);
    }

    public String getMemberRoleTypeByUserId(String userId){
        return userRepository.getMemberRoleTypeByUserId(userId);
    }
}
