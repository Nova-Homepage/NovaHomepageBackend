package Nova.Post.Dto;

import Nova.Post.Dto.PostDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
public class PagingDto {
    Page<PostDto> postlist;
    Integer startPage;
    Integer endPage;
}
