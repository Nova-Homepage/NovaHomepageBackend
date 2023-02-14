package Nova.Post.service;

import Nova.Post.Dto.PostDto;
import Nova.Post.domain.Post;
import Nova.Post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor //생성자를 자동으로 생성해서 의존관계를 주입해줌?
public class PostService {

    //DTO -> ENTITY
    //ENTITIY -> DTO
    //서비스 클래스에서 변환하는 과정이 많이 일어난다. VIEW에서 ENTITIY를 많이 노출시키지 않기 위해서 DTO를 사용한다.

    private final PostRepository postRpo; //필드주입

    @Transactional(readOnly = false)
    public Long save (PostDto postDto)
    {
        Post post = Post.toSaveEntity(postDto);
        postRpo.save(post);
        return post.getId();
    }
    public List<PostDto> findAll()
    {
        List<Post> postList= postRpo.findAll();
        List<PostDto> postDtoList = new ArrayList<>();
        for(Post post : postList)
        {
            postDtoList.add(PostDto.toPostDto(post)); //entitiy -> dto
        }
        return postDtoList;

    }
    public PostDto findById(Long id)
    {
        Optional<Post> Optinalpost = postRpo.findById(id);
        if(Optinalpost.isPresent()) {
            Post post = Optinalpost.get();
            PostDto postDto = PostDto.toPostDto(post);
            return postDto;
        }
        else {
            return null;
        }
    }
    @Transactional(readOnly = false)
    public PostDto upDate(PostDto postDto) //JPQ는 save 메서드를 통해서 insert update를 진행하는데, id값이 주어지면 update 없다면 insert를 실행한다.
    {
        PostDto original = findById(postDto.getId());

        Post post = Post.toUpdateEntity(postDto);
        //save 될동안 create , modify 시간이 널이 되서 미리 가져와서 넣음
        //안좋은 코드긴한데 시간차이가 크지 않아서 사용
        //이유를 아시나요 ㅠㅠㅠㅠ
        post.setCreatedDate(original.getCreatedDate());
        post.setModifiedDate(LocalDateTime.now());

        postRpo.save(post);
        return findById(postDto.getId());
    }

    //게시글 삭제
    @Transactional(readOnly = false)
    public void delete(Long id) {
        postRpo.deleteById(id); //게시글 삭제
    }

    public Page<PostDto> paging(Pageable pageable) {
        int page = pageable.getPageNumber() -1;
        int pageLimit= 3;
        //한페이지당 3개씩 글을 보여주고 정렬 기준은 id 기준으로 내림차순 정렬
        //page 위치에 있는 값은 0부터 시작
        Page<Post> postEntities = postRpo.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        //page : 보고싶은페이지
        //pageLimit : 한페이지에 보여줄 글 갯수
        //id를 기준으로 내림차순 -> 최신글이 먼저

        System.out.println("boardEntities.getContent() = " + postEntities.getContent()); // 요청 페이지에 해당하는 글
        System.out.println("boardEntities.getTotalElements() = " + postEntities.getTotalElements()); // 전체 글갯수
        System.out.println("boardEntities.getNumber() = " + postEntities.getNumber()); // DB로 요청한 페이지 번호
        System.out.println("boardEntities.getTotalPages() = " + postEntities.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardEntities.getSize() = " + postEntities.getSize()); // 한 페이지에 보여지는 글 갯수
        System.out.println("boardEntities.hasPrevious() = " + postEntities.hasPrevious()); // 이전 페이지 존재 여부
        System.out.println("boardEntities.isFirst() = " + postEntities.isFirst()); // 첫 페이지 여부
        System.out.println("boardEntities.isLast() = " + postEntities.isLast()); // 마지막 페이지 여부

        //post는 entitiy라고 생각하면 된다 entitiy->를 dto로 바꿔주는데 Page자체는 그대로 가져가 page 메서드를 사용할 수 있음
        //목록 : id , 제목, 내용, 카테고리,생성일자,변경일자
        Page<PostDto> postDtos = postEntities.map(post -> new PostDto(
                post.getId(),post.getTitle(),post.getContent(),post.getTag(),post.getCreatedDate(),post.getModifiedDate()
        )
            );
        return postDtos;

    }
}
