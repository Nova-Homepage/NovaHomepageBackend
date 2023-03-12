package com.board.novaapi.Controller;

import com.board.novaapi.OAuth.Entity.RoleType;
import com.board.novaapi.common.ApiResponse;
import com.board.novaapi.dto.userDTO.SimpleUserInfoDto;

import com.board.novaapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final static long THREE_DAYS_MSEC = 259200000;
    private final static String REFRESH_TOKEN = "refresh_token";

    /***
     * 모든멤버의 요약정보 반환
     * @return SimpleUserInfoDto
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/memberrtype")
    public ApiResponse getAllSimpleMemberInfo(){
        return ApiResponse.success("success",authService.getAllMemberInfo().stream().map(SimpleUserInfoDto::new));
    }

    /***
     *
     * @param userId 멤버 아이디
     * @param param 변경 할 권한
     * 잘못된 권한에 대한 예외처리 방법을 알아 볼 것.
     */
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/memberrtype/{userId}")
    public void UpdateMembersRoleType(@PathVariable("userId") String userId, @RequestBody Map<String,RoleType> param){
        RoleType roleType = param.get("roleType");
        authService.UpdateMemberRoleType(userId,roleType);
    }

}
