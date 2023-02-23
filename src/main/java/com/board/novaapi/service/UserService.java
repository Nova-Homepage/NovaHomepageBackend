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

    public String getMemberRoleTypeByUserId(String userId){
        return userRepository.getMemberRoleTypeByUserId(userId);
    }
}