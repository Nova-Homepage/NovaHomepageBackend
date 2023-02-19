package Nova.Post.service;

import Nova.Post.Dto.PostDto;
import Nova.Post.domain.Post;
import Nova.Post.domain.TagState;
import Nova.Post.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class) //junit에게 스프링부트 관련하여 테스트를 진행할거라는걸 알려줌
@WebAppConfiguration
@SpringBootTest
@Transactional //테스트에서 사용되게 되면 처음 db를 다 롤백시킴
@Rollback(value = false) //테스트후 롤백을 안시킴
class PostServiceTest {

    @Autowired PostService postService;

    @Test
    void 회원가입() {
        PostDto postDto = new PostDto();
        postDto.setTag(TagState.NOTICE);
//        postService.save(postDto);
    }
    @Test
    void 회원조회()
    {
        PostDto postDto = new PostDto();
        postDto.setTag(TagState.NOTICE);
//        postService.save(postDto);

        PostDto postDto1 = new PostDto();
        postDto1.setTag(TagState.NOTICE);
//        postService.save(postDto1);


        System.out.println(postService.findAll().toString());
    }
    @Test
    void 상세페이지()
    {
        PostDto postDto = new PostDto();
        postDto.setTag(TagState.NOTICE);
//        postService.save(postDto);

        PostDto postDto1 = new PostDto();
        postDto1.setTag(TagState.ANSWER);
//        postService.save(postDto1);

        postService.findAll();
        postService.findById(1L);
    }
}