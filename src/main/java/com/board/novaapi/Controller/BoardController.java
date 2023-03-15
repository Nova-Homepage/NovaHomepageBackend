package com.board.novaapi.Controller;


import com.board.novaapi.dto.boarddto.BoardDto;
import com.board.novaapi.entity.board.BoardEntity;
import com.board.novaapi.service.BoardService;
import com.board.novaapi.service.CommentService;
import com.board.novaapi.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor //생성자 생성과 의존관계를 진행해줌
@RequestMapping("/board") //해당 컨트롤러에서 /post를 가장 앞 uri에 위치시킨다.
@RestController
@Api(tags = {"Board Info"}, description = "게시판 서비스")
public class BoardController {

    private final BoardService boardService;
    private final FileService fileService;
    private final CommentService commentService;
    @PostMapping(value = "/save" , consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE} )
    public String createPost(@RequestPart BoardDto boardDto, @RequestPart(value = "files",required = false) List<MultipartFile> files) throws IOException {
        if(files.isEmpty()) //null인데 왜자꾸 다른데  들어가는거냐
        { //파일이 없을대
            boardService.save(boardDto);
        }
        else {
            //첨부파일이 있을때
            BoardEntity save = boardService.save(boardDto);
            fileService.uploadfile(files,save);

        }
        return "success";
    }

    //글목록
    @ApiOperation(value = "전체 게시글 조회", notes = "전체 게시글을 조회한다")
    @GetMapping("/")
    public List<BoardDto> PostAll()
    {
        return boardService.findAll();
    }


    @ApiOperation(value = "게시글 상세페이지 조회", notes = "특정 게시글을 조회한다\n " +
            "id는 boardid를 프론트에서 넘겨주면된다.\n" +
            "/?page=3&size=4 와 같이 파라미터를 전달해주면 됩니다\n")
    @GetMapping("/{id}")
    public ResponseEntity<Map<String,Object>> findById(@PathVariable Long id , @PageableDefault(page=1) Pageable pageable)
    {

        if(boardService.findById(id) == null)
        {
            return ResponseEntity.notFound().build();
        }
        else {
            BoardDto boardDto = boardService.findById(id);
            List<Map<String, Object>> commentDtoList = commentService.findAll(boardDto.getId());//게시글의 모든 댓글 가져오기
            Map<String, Object> result = new HashMap<>();
            result.put("post", boardDto);
            result.put("comment",commentDtoList);
            return ResponseEntity.ok().body(result);
        }
    }

    @ApiOperation(value = "수정할 게시글 보기")
    @GetMapping("/update/{id}")
    public BoardDto updatePost(@PathVariable Long id)
    {
        BoardDto boardDto = boardService.findById(id); //수정하기전에 기존 정보들을 가져옴
        return boardDto;
    }

    @PostMapping("/update")
    @ApiOperation(value = "게시글 수정")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "BoardDto", value = "게시판 PK", required = true,paramType = "Long",defaultValue = ""),
            @ApiImplicitParam(name = "title", dataType = "BoardDto", value = "제목" ,required = false,paramType = "String"),
            @ApiImplicitParam(name = "content", dataType = "BoardDto", value = "내용" ,required = false,paramType = "String"),
            @ApiImplicitParam(name = "tag", dataType = "BoardDto", value = "글 카테고리" ,required = false,paramType = "String") //embedded는 어케 표시해야되나
    })
    public BoardDto update(@RequestBody BoardDto boardDto)
    {
        BoardDto updateBoardDto = boardService.upDate(boardDto);
        return updateBoardDto;
    }

    @ApiOperation(value = "게시글 삭제", notes = "특정 게시글을 삭제한다")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id)
    {
        boardService.delete(id);
        return id+"번 게시글 삭제성공";
    }
    @ApiOperation(value = "게시글 목록 페이징", notes = "ex) 게시글을 목록으로 나타냄")
    @GetMapping("/paging")
    public Page<BoardDto> paging(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable)
    {
        return boardService.paging(pageable);
    }

}
