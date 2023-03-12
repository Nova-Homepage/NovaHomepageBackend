package com.board.novaapi.Controller;

import com.board.novaapi.common.ApiResponse;
import com.board.novaapi.dto.userDTO.UserProfileDto;
import com.board.novaapi.dto.userDTO.UserProfileUpdateDto;
import com.board.novaapi.entity.user.User;
import com.board.novaapi.entity.user.UserProfile;
import com.board.novaapi.repository.user.UserRepository;
import com.board.novaapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    /***
     *
     * @return ApiResponse 반환
     */
    @GetMapping("/test")
    public ApiResponse getUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userService.getUser(principal.getUsername());
        return ApiResponse.success("user", user);
    }

    /***
     * user id 에 맞는 user profile을 가져온다
     * 프로필은 하나뿐임으로 페이징을 적용하지 않는다.
     * 유저 테이블에 userId가 존재하는지 확인
     * @return ApiResponse.success and .fail()
     */
    @GetMapping("/profile/one/{userId}")
    public ApiResponse getOneUserProfile(@PathVariable String userId){

        /***
         * 해당부분을 exist를 사용한 jpa repository 로 대체할 예정 (성능상 이슈)
         */
        if(userRepository.checkUserIdExist(userId)){
            UserProfileDto profileDto = new UserProfileDto(userService.getOneUserProfile(userId));
            return ApiResponse.success("userProfile",profileDto);
        }else{
            return ApiResponse.fail();
        }

    }

    /***
     * 모든 user profile을 가져온다
     * @return Page<UserProfileDto>
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/profile/all")
    public ApiResponse getAllUserProfile(){
        return ApiResponse.success("userProfile",userService.getAllUserProfile().stream().map(UserProfileDto::new));
    }

    /**
     * 유저 프로필 정보 수정
     * @param userId
     * @param userProfileUpdateDto
     */
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("profile/update/{userId}")
    public void UpdateUserProfile(@PathVariable("userId") String userId, @RequestBody UserProfileUpdateDto userProfileUpdateDto){

        userService.updateUserProfile(userId, userProfileUpdateDto);
    }

    /* 유저프로필 update 를 위한 함수 // 보류.
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("profile/update/img/{userId}")
    public void UpdateUserProfileImg(@PathVariable("userId") String userId, @RequestBody MultipartFile profileimg){

    }
*/
    @GetMapping("/hello")
    public String InfoAll(){
        return "hello_all";
    }



}