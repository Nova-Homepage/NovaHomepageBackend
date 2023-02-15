package com.board.novaapi.Controller;

import com.board.novaapi.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

}
