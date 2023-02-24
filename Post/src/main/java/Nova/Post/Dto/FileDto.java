package Nova.Post.Dto;

import Nova.Post.domain.BoardEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

    private BoardEntity boardEntity;
}
