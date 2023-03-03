package com.board.novaapi.service;


import com.board.novaapi.dto.userDTO.UserProfileDto;
import com.board.novaapi.entity.user.User;
import com.board.novaapi.entity.user.UserProfile;
import com.board.novaapi.repository.user.UserProfileRepository;
import com.board.novaapi.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
     * UserProfile을 가져오기 위한 함수
     * @param userId
     * @return
     */
    @Transactional(readOnly = true)
    public UserProfile getOneUserProfile(String userId){

        return userRepository.findByUserId(userId).getUserProfile();
    }

    @Transactional(readOnly = true)
    public Page<UserProfile> getAllUserProfile(Pageable pageable){
        return userProfileRepository.findAll(pageable);
    }

    /***
     *
     * @param userId
     * @param userProfileDto
     */
    @Transactional(readOnly = false)
    public void updateUserProfile(String userId, UserProfileDto userProfileDto){
        // profile 존재여부 확인
        Long ProfileSeq = userRepository.findByUserId(userId).getUserProfile().getUserProfileSeq();
        userProfileRepository.updateProfileByUserSeq(ProfileSeq,
                userProfileDto.getProfileComment(),
                userProfileDto.getPhone(),
                userProfileDto.getStudentId(),
                userProfileDto.getBlogInfo());
    }

}