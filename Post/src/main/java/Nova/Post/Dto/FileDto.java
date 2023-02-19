package Nova.Post.Dto;

import Nova.Post.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@ToString
@NoArgsConstructor // 기본생성자
public class FileDto {
    private Long id;

    private String originalFileName;

    private String storedFileName;

    private String file_path;

    private String file_type;

    private Long file_size;

    private Post post;
}
