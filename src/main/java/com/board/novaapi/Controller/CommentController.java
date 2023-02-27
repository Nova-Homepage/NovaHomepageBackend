package com.board.novaapi.Controller;


import com.board.novaapi.dto.boarddto.CommentDto;
import com.board.novaapi.service.CommentService;
import com.board.novaapi.service.ReplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor //생성자 생성과 의존관계를 진행해줌
@RequestMapping("/comment") //해당 컨트롤러에서 /post를 가장 앞 uri에 위치시킨다.
@RestController
@Api(tags = {"Comment Info"}, description = "댓글 서비스")
public class CommentController {
    private final CommentService commentService;
    private final ReplyService replyService;
    //작성자, 게시글, 댓글내용을 전달 받아야한다.
    @PostMapping("/save")
    @ApiOperation(value = "댓글 등록")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "boardId", dataType = "CommentDto", value = "게시판 PK", required = true,paramType = "Long"),
            @ApiImplicitParam(name = "commentWriter", dataType = "CommentDto", value = "댓글쓰는 유저이름" ,required = false,paramType = "String"),
            @ApiImplicitParam(name = "commentContents", dataType = "CommentDto", value = "댓글내용" ,required = false,paramType = "String")
    })
    public ResponseEntity save(@RequestBody CommentDto commentDto)
    {
        Long result = commentService.save(commentDto);
        if(result != null)
        {
            List<Map<String, Object>> commentDTOlist = commentService.findAll(commentDto.getBoardId());
            return new ResponseEntity<>(commentDTOlist, HttpStatus.OK); //저장후에 게시글 조회
        }
        else {
            return new ResponseEntity<>("해당 게시글이 존재하지 않습니다",HttpStatus.NOT_FOUND); //바디와 상태를 같이 보냄
        }
    }
    @ApiOperation(value = "댓글 수정")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "boardId", dataType = "CommentDto", value = "게시판 PK", required = true,paramType = "Long"),
            @ApiImplicitParam(name = "commentWriter", dataType = "CommentDto", value = "댓글쓰는 유저이름" ,required = false,paramType = "String"),
            @ApiImplicitParam(name = "commentContents", dataType = "CommentDto", value = "댓글내용" ,required = false,paramType = "String"),
            @ApiImplicitParam(name = "id", dataType = "CommentDto", value = "댓글id" ,required = true,paramType = "Long")
    })
    @PostMapping("/update")
    public CommentDto update(@RequestBody CommentDto commentDto)
    {
        CommentDto update = commentService.update(commentDto);
        return update;
    }
    @ApiOperation(value = "댓글 삭제", notes = "특정 댓글을 삭제한다")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id)
    {
        commentService.delete(id);
        return "댓글 삭제";
    }


}
