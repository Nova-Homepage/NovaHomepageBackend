package Nova.Post.controller;

import Nova.Post.Dto.PostDto;
import Nova.Post.Dto.PagingDto;
import Nova.Post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor //생성자 생성과 의존관계를 진행해줌
@RequestMapping("/post") //해당 컨트롤러에서 /post를 가장 앞 uri에 위치시킨다.
@RestController
public class PostController {

    private final PostService postService;

    //글쓰기
    @GetMapping("/save")
    public String Save()
    {
        return "save";
    }
    @PostMapping("/save")
    public String createPost(@RequestBody @Valid PostDto postDto)
    {
        postService.save(postDto);
        return "게시글 생성";
    }

    //글목록
    @GetMapping("/")
    public List<PostDto> PostAll()
    {
        return postService.findAll();
    }
    //상세페이지  paging?page=1
    @GetMapping("/{id}")
    public PostDto findById(@PathVariable Long id ,@PageableDefault(page=1) Pageable pageable)
    {
        //PostDto 객체가 위치한 페이지를 PostDto가 가지고 있어야 할듯?
        //그때그때 위치한 게시글의 페이지가 바뀌니까 여기서 넣어주는게 맞을듯?
        PostDto postDto = postService.findById(id);
        postDto.setPreviouspage(pageable.getPageNumber()); //상세페이지를 들어가고 나왔을때 이전 page를 알고 있어야한다.
        return  postDto;
    }

    //글 수정페이지로 이동
    @GetMapping("/update/{id}")
    public PostDto updatePost(@PathVariable Long id)
    {
        PostDto postDto = postService.findById(id); //수정하기전에 기존 정보들을 가져옴
        return postDto;
    }

    //글수정 entity -> dto
    @PostMapping("/update")
    public PostDto update(@RequestBody PostDto postDto)
    {
        PostDto updatePostDto = postService.upDate(postDto);
        return updatePostDto;
    }

    //게시글 삭제
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id)
    {
        postService.delete(id);
        return id+"번 게시글 삭제성공";
    }

    //게시글 페이징
    //   post/paging?page=1   //기본적으로 1페이지를 주겠다
    @GetMapping("/paging")
    public PagingDto paging(@PageableDefault(page = 1)Pageable pageable) //@requestparam을 이용해도 된다.
    {
//        pageable.getPageNumber(); //어떤 페이지가 요청이 되었나
        Page<PostDto> postList = postService.paging(pageable);
        int blockLimit = 3; //밑에 보여지는 페이지 번호 개수
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
        int endPage = ((startPage + blockLimit - 1) < postList.getTotalPages()) ? startPage + blockLimit - 1 : postList.getTotalPages(); //3 6 9 12..
        //page 갯수가 20개 라면
        //현재 사용자가 3 페이지
        //1 2 3
        //현재 사용자가 7페이지
        //7 8 9
        // 보여지는 페이지 갯수 3개
        // 총 페이지 갯수가 8개 라면
        PagingDto pagingDto = new PagingDto();
        pagingDto.setPostlist(postList);
        pagingDto.setStartPage(startPage);
        pagingDto.setEndPage(endPage);

        return pagingDto;
    }

}
