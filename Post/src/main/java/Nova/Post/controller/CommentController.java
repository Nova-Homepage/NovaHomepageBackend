package Nova.Post.controller;

import Nova.Post.Dto.CommentDto;
import Nova.Post.Dto.PostDto;
import Nova.Post.Dto.ReplyDto;
import Nova.Post.service.CommentService;

import Nova.Post.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor //생성자 생성과 의존관계를 진행해줌
@RequestMapping("/comment") //해당 컨트롤러에서 /post를 가장 앞 uri에 위치시킨다.
@RestController
public class CommentController {
    private final CommentService commentService;
    private final ReplyService replyService;
    //작성자, 게시글, 댓글내용을 전달 받아야한다.
    @PostMapping("/save")
    public ResponseEntity save(@RequestBody CommentDto commentDto)
    {
        Long result = commentService.save(commentDto);
        if(result != null)
        {
            List<Map<String, Object>> commentDTOlist = commentService.findAll(commentDto.getPostId());
            return new ResponseEntity<>(commentDTOlist, HttpStatus.OK); //저장후에 게시글 조회
        }
        else {
            return new ResponseEntity<>("해당 게시글이 존재하지 않습니다",HttpStatus.NOT_FOUND); //바디와 상태를 같이 보냄
        }
    }
    //댓글수정 entity -> dto
    @PostMapping("/update")
    public CommentDto update(@RequestBody CommentDto commentDto)
    {
        CommentDto update = commentService.update(commentDto);
        return update;
    }
    //댓글 삭제
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id)
    {
        commentService.delete(id);
        return "댓글 삭제";
    }


}
