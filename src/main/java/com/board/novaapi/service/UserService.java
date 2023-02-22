package com.board.novaapi.service;


import com.board.novaapi.OAuth.Entity.RoleType;
import com.board.novaapi.entity.user.User;
import com.board.novaapi.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    // json 형태로 제공
    // paging 기능 제공해야함.

    //dto를 만들어서 반환해 주는 방법이 읽기에 깔끔함.
    @Transactional
    public List<Map<String, Object>> getAllSimpleMemberInfo(){

        List<User> user = userRepository.findAll();
        System.out.println(user);
        List<Map<String,Object>> simpleMemberInfo = new ArrayList<>();

        for(User u : user){
            Map<String, Object> result = new HashMap<>();
            result.put("userId",u.getUserId());
            result.put("username",u.getUsername());
            result.put("roleType",u.getRoleType());
            System.out.println(simpleMemberInfo);
            simpleMemberInfo.add(result);
        }
        System.out.println(simpleMemberInfo);
        return simpleMemberInfo;
    }

    //
    public void UpdateMemberRoleType(String userId, String stringRoleType){
        //string roleType를 변경해주어야 함.
        //System.out.println("update 시 주입된 roletype 확인 : " + stringRoleType);
        /**
         * log 사용해서 남기기.
         */
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
