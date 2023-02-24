package Nova.Post.service;

import Nova.Post.Dto.BoardDto;
import Nova.Post.domain.TagState;
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
class BoardEntityServiceTest {

    @Autowired
    BoardService boardService;

    @Test
    void 회원가입() {
        BoardDto boardDto = new BoardDto();
        boardDto.setTag(TagState.NOTICE);
//        postService.save(postDto);
    }
    @Test
    void 회원조회()
    {
        BoardDto boardDto = new BoardDto();
        boardDto.setTag(TagState.NOTICE);
//        postService.save(postDto);

        BoardDto boardDto1 = new BoardDto();
        boardDto1.setTag(TagState.NOTICE);
//        postService.save(postDto1);


        System.out.println(boardService.findAll().toString());
    }
    @Test
    void 상세페이지()
    {
        BoardDto boardDto = new BoardDto();
        boardDto.setTag(TagState.NOTICE);
//        postService.save(postDto);

        BoardDto boardDto1 = new BoardDto();
        boardDto1.setTag(TagState.ANSWER);
//        postService.save(postDto1);

        boardService.findAll();
        boardService.findById(1L);
    }
}