package Nova.Post.Dto;

import Nova.Post.domain.CommentEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class CommentDto {
    private Long id;
    private String commentWriter;
    private String commentContents;
    private Long postId;
    private LocalDateTime commentCreatedTime;
    private LocalDateTime commentUpdatedTime;

    public static CommentDto toCommentDto(CommentEntity commentEntity) {
        CommentDto commentDto = new CommentDto();
        commentDto.setCommentContents(commentEntity.getCommentContents());
        commentDto.setCommentWriter(commentEntity.getCommentWriter());
        commentDto.setId(commentEntity.getId());
        commentDto.setPostId(commentEntity.getPost().getId());
        commentDto.setCommentCreatedTime(commentEntity.getCreatedDate());
        commentDto.setCommentUpdatedTime(commentEntity.getModifiedDate());
        return commentDto;

    }
}
