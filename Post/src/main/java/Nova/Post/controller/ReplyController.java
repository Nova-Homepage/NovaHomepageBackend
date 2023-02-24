package Nova.Post.controller;

import Nova.Post.Dto.ReplyDto;
import Nova.Post.service.ReplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor //생성자 생성과 의존관계를 진행해줌
@RequestMapping("/reply") //해당 컨트롤러에서 /post를 가장 앞 uri에 위치시킨다.
@RestController
@Api(tags = {"Reply Info"}, description = "대댓글 서비스")
public class ReplyController {
    private final ReplyService replyService;
    //대댓글 저장
    @PostMapping("/save")
    @ApiOperation(value = "대댓글 등록")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commenttId", dataType = "ReplyDto", value = "댓글 PK", required = true,paramType = "Long"),
            @ApiImplicitParam(name = "commentWriter", dataType = "ReplyDto", value = "대댓글쓰는 유저이름" ,required = false,paramType = "String"),
            @ApiImplicitParam(name = "commentContents", dataType = "ReplyDto", value = "대댓글내용" ,required = false,paramType = "String"),
    })
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
    //대댓글 수정
    @PostMapping("/update")
    @ApiOperation(value = "대댓글 수정")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commenttId", dataType = "ReplyDto", value = "댓글 PK", required = true,paramType = "Long"),
            @ApiImplicitParam(name = "commentWriter", dataType = "ReplyDto", value = "대댓글쓰는 유저이름" ,required = false,paramType = "String"),
            @ApiImplicitParam(name = "commentContents", dataType = "ReplyDto", value = "대댓글내용" ,required = false,paramType = "String"),
            @ApiImplicitParam(name = "id", dataType = "ReplyDto", value = "대댓글id" ,required = true,paramType = "Long")
    })
    public ReplyDto update(@RequestBody ReplyDto replyDto)
    {
        ReplyDto update = replyService.update(replyDto);
        return update;
    }

    @ApiOperation(value = "대댓글 삭제", notes = "특정 대댓글을 삭제한다")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id)
    {
        replyService.delete(id);
        return "대댓글 삭제";
    }
}
