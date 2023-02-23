package com.board.novaapi.Controller;

import com.board.novaapi.OAuth.Entity.RoleType;
import com.board.novaapi.repository.user.UserRepository;
import com.board.novaapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final UserService userService;

    /***
     *
     * @return 요약 멤버 정보 반환 (userId, username, roleType
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/memberrtype")
    public List<Map<String,Object>> getAllSimpleMemberInfo(){
        return userService.getAllSimpleMemberInfo();
    }

    /***
     *
     * @param userId 유저 고유 아이디
     * @param param roleType
     * 성공시 HttpStatus 200 OK 반환
     */
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/memberrtype/{userId}")
    public void UpdateMembersRoleType(@PathVariable("userId") String userId, @RequestBody Map<String,RoleType> param){
        RoleType roleType = param.get("roleType");
        userService.UpdateMemberRoleType(userId,roleType);
    }

}
