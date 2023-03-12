package com.board.novaapi.service;


import com.board.novaapi.dto.userDTO.UserProfileDto;
import com.board.novaapi.dto.userDTO.UserProfileUpdateDto;
import com.board.novaapi.entity.user.User;
import com.board.novaapi.entity.user.UserProfile;
import com.board.novaapi.repository.user.UserProfileRepository;
import com.board.novaapi.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    // User Repository start
    @Transactional(readOnly = true)
    public User getUser(String userId) {
        return userRepository.findByUserId(userId);
    }

    // UserProfileRepository start

    /***
     * 한명의 UserProfile을 가져오는 함수
     * @param userId
     * @return
     */
    @Transactional(readOnly = true)
    public UserProfile getOneUserProfile(String userId){
        return userRepository.findByUserId(userId).getUserProfile();
    }

    /***
     * 모든 사용자의 프로필을 가져오는 함수
     * @return
     */
    @Transactional(readOnly = true)
    public List<UserProfile> getAllUserProfile(){
        return userProfileRepository.findAll();
    }

    /**
     *
     * @param userId
     * @param userProfileUpdateDto
     */
    @Transactional(readOnly = false)
    public void updateUserProfile(String userId, UserProfileUpdateDto userProfileUpdateDto){
        // profile 존재여부 확인
        Long ProfileSeq = userRepository.findByUserId(userId).getUserProfile().getUserProfileSeq();
        userProfileRepository.updateProfileByUserSeq(ProfileSeq,
                userProfileUpdateDto.getProfileComment(),
                userProfileUpdateDto.getPhone(),
                userProfileUpdateDto.getStudentId(),
                userProfileUpdateDto.getBlogInfo());
    }

}