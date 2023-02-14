package Nova.Post.controller;

import Nova.Post.Dto.PostDto;
import Nova.Post.domain.Post;
import Nova.Post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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
    //상세페이지
    @GetMapping("/{id}")
    public PostDto findById(@PathVariable Long id)
    {
        return postService.findById(id);
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
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id)
    {
        postService.delete(id);
        return id+"번 게시글 삭제성공";
    }

}
