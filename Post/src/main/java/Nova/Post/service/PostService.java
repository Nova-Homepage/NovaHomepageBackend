package Nova.Post.service;

import Nova.Post.Dto.PostDto;
import Nova.Post.domain.Post;
import Nova.Post.repository.PostFileRepository;
import Nova.Post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor //생성자를 자동으로 생성해서 의존관계를 주입해줌?
public class PostService {

    //DTO -> ENTITY
    //ENTITIY -> DTO
    //서비스 클래스에서 변환하는 과정이 많이 일어난다. VIEW에서 ENTITIY를 많이 노출시키지 않기 위해서 DTO를 사용한다.

    private final PostRepository postRpo; //필드주입

    @Transactional(readOnly = false)
    public Post save (PostDto postDto) throws IOException {
            Post post = Post.toSaveEntity(postDto);
            Post save = postRpo.save(post);
            return save;
    }
    @Transactional(readOnly = false) //부모에서 자식 엔티티를 접그할때 트렌잭션을 선언해줘야한다
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
    @Transactional(readOnly = false)
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

    //페이징처리
    public Page<PostDto> paging(Pageable pageable) {

        Page<Post> PageEntitiies = postRpo.findAll(pageable);
        Page<PostDto> postDtos = PageEntitiies.map(post -> new PostDto(post.getId(),post.getTitle(),post.getContent(),post.getTag(),post.getCreatedDate(),post.getModifiedDate()
       ));
        return postDtos;

    }
}
