package Nova.Post.domain;

import Nova.Post.Dto.PostDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//DTO (Data Transfer Object) 데이터 전송 객체 -> Entity를 가리는것 같다.
@Entity
@Getter @Setter
@NoArgsConstructor //기본생성자
@AllArgsConstructor //모든 필드를 매개변수로 하는 생성자
public class Post extends BaseTimeEntity{ //자바에서 상속받은 부모 클래스도 JPA에서는 한 객체로 인식해서 DATA 테이블에 넣어줌
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    @Column(name ="post_id")
    private Long id;

    @Column
    private String title;

    @Column // default 255 ,null 가능  //length = 20 , nullable = false 로 조건 가능
    private String content;

    @Enumerated(EnumType.STRING) //DB에 저장되는 방식인듯
    private TagState tag;

    @OneToMany(mappedBy ="post", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<FileEntity> FileEntityList = new ArrayList<>(); //여러개가 올수 있도록 참조관계 설정 -> db에 저장되는건 아님


    //entitiy -> dto
    public static Post toSaveEntity(PostDto postDto)
    {
        Post post = new Post();
        post.setTag(postDto.getTag());
        post.setContent(postDto.getContent());
        post.setTitle(postDto.getTitle());

        return post;

    }

    public static Post toUpdateEntity(PostDto postDto) {
        Post post = new Post();
        post.setId(postDto.getId()); //아주 중요!
        post.setTag(postDto.getTag());
        post.setContent(postDto.getContent());
        post.setTitle(postDto.getTitle());
//        post.setCreatedDate(postDto.getCreatedDate());
//        post.setModifiedDate(LocalDateTime.now());


        return post;
    }
}
