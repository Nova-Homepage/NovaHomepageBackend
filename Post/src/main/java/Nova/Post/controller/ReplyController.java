package Nova.Post.controller;

import Nova.Post.Dto.ReplyDto;
import Nova.Post.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor //생성자 생성과 의존관계를 진행해줌
@RequestMapping("/reply") //해당 컨트롤러에서 /post를 가장 앞 uri에 위치시킨다.
@RestController
public class ReplyController {
    private final ReplyService replyService;
    //대댓글 저장
    @PostMapping("/save")
    public ResponseEntity replysave(@RequestBody ReplyDto replyDto)
    {
        Long result = replyService.save(replyDto);
        if(result!=null)
        {
            return new ResponseEntity<>("일단 대댓글 성공", HttpStatus.OK); //저장후에 게시글 조회
        }
        else {
            return new ResponseEntity<>("댓글이 존재하지 않습니다",HttpStatus.NOT_FOUND); //바디와 상태를 같이 보냄
        }
    }
}
