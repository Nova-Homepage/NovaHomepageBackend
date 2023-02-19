package Nova.Post.controller;

import Nova.Post.Dto.PostDto;
import Nova.Post.Dto.PagingDto;
import Nova.Post.domain.FileEntity;
import Nova.Post.domain.Post;
import Nova.Post.service.LocalService;
import Nova.Post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RequiredArgsConstructor //생성자 생성과 의존관계를 진행해줌
@RequestMapping("/post") //해당 컨트롤러에서 /post를 가장 앞 uri에 위치시킨다.
@Transactional
@RestController
public class PostController {

    private final PostService postService;
    private final LocalService localService;

    //글쓰기
    @PostMapping(value = "/save" , consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE} )
    public String createPost(@RequestPart PostDto postDto, @RequestPart(value = "files",required = false) List<MultipartFile> files) throws IOException {
        if(files.isEmpty())
        { //파일이 없을대
            postService.save(postDto);
        }
        else {
            //첨부파일이 있을때
            Post save = postService.save(postDto);
            localService.uploadfile(files,save);

        }
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
    public Page<PostDto> paging(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable)
    {
        return postService.paging(pageable);
    }


    @Autowired
    private ResourceLoader resourceLoader;
    //파일 서비스
    @GetMapping("/images/{fileId}")
    public String downloadImage(@PathVariable("fileId") Long id) throws IOException{


        String aa = "<img src=\""+localService.downloadImage(id).getFile() + "\">";
        return aa;
//        return localService.downloadImage(id);
    }

    @GetMapping("/image/all")
    public List<FileEntity> findfileAll()
    {
        return localService.findAll();
    }
    // 첨부 파일 다운로드
    @GetMapping("/attach/{id}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long id) throws MalformedURLException {

        FileEntity file = localService.findById(id);

        UrlResource resource = new UrlResource("file:" + file.getFile_path());

        String encodedFileName = UriUtils.encode(file.getOriginalFileName(), StandardCharsets.UTF_8);

        // 파일 다운로드 대화상자가 뜨도록 하는 헤더를 설정해주는 것
        // Content-Disposition 헤더에 attachment; filename="업로드 파일명" 값을 준다.
        String contentDisposition = "attachment; filename=\"" + encodedFileName + "\"";

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,contentDisposition).body(resource);
    }

}
