package com.board.novaapi.Controller;

import com.board.novaapi.OAuth.Entity.RoleType;
import com.board.novaapi.dto.SimpleUserInfoDto;
import com.board.novaapi.repository.user.UserRepository;
import com.board.novaapi.service.AuthService;
import com.board.novaapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    /***
     *
     * @param pageable
     * @return 모든 멤버의 요약 정보를 반환
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/memberrtype")
    public Page<SimpleUserInfoDto> getAllSimpleMemberInfo(Pageable pageable){
        return authService.getAllMemberInfo(pageable).map(SimpleUserInfoDto::new);
    }

    /***
     *
     * @param userId 멤버 아이디
     * @param param 변경 할 권한
     */
    @PutMapping("/memberrtype/{userId}")
    public void UpdateMembersRoleType(@PathVariable("userId") String userId, @RequestBody Map<String,RoleType> param){
        RoleType roleType = param.get("roleType");
        authService.UpdateMemberRoleType(userId,roleType);
    }

}
