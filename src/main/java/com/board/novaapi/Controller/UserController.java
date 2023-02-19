package com.board.novaapi.Controller;

import com.board.novaapi.common.ApiResponse;
import com.board.novaapi.entity.user.User;
import com.board.novaapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
@RequiredArgsConstructor
public class UserController {

    // 유저 정보를 위한 Controller

    private final UserService userService;

    @GetMapping("/test")
    public ApiResponse getUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userService.getUser(principal.getUsername());

        return ApiResponse.success("user", user);
    }

    // 좋은 uri 란 무엇일까 생각해보자.
    // 리소스를 잘 식별할 수 있어야 한다.

    @GetMapping("/guest")
    public String InfoGuest(){
        return "hello_guest";
    }

    @GetMapping("/user")
    public String InfoUser(){
        return "hello_user";
    }

    @GetMapping("/all")
    public String InfoAll(){
        return "hello_all";
    }



}