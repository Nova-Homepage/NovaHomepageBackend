package com.board.novaapi.Controller;

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
    //유저 권한 컨트롤러
    //member role type 확인 및 변경
    //유저 검색기능 있어야함.
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/memberrtype")
    public List<String> getAllSimpleMemberInfo(){
        return userRepository.getAllSimpleMemberInfo();
    }

    // 유저 아이디, 유저 권한타입

    @Transactional
    @PutMapping("/memberrtype/{userId}")
    public String UpdateMembersRoleType(@PathVariable("userId") String userId, @RequestBody Map<String,String> param){
        String roleType = param.get("roleType");
        userService.UpdateMemberRoleType(userId,roleType);
        return "redirect://auth/memberrtype";
    }

}
