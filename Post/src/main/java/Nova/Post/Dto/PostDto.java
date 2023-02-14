package Nova.Post.Dto;

import Nova.Post.domain.Post;
import Nova.Post.domain.TagState;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
@Getter
@Setter
@ToString
@NoArgsConstructor // 기본생성자
// @AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private TagState tag;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    private Integer previouspage;


    public PostDto(Long id, String title, String content, TagState tag, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.tag = tag;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    //entitiy -> dto
    public static PostDto toPostDto(Post post)
    {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTag(post.getTag());
        postDto.setContent(post.getContent());
        postDto.setTitle(post.getTitle());
        postDto.setCreatedDate(post.getCreatedDate());
        postDto.setModifiedDate(post.getModifiedDate());


        return postDto;
    }
}
