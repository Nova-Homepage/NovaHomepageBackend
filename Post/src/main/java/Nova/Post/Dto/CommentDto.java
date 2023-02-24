package Nova.Post.Dto;

import Nova.Post.domain.CommentEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CommentDto {
    private Long id;
    private String commentWriter;
    private String commentContents;
    private Long boardId;
    private LocalDateTime commentCreatedTime;
    private LocalDateTime commentUpdatedTime;

    public static CommentDto toCommentDto(CommentEntity commentEntity) {
        CommentDto commentDto = new CommentDto();
        commentDto.setCommentContents(commentEntity.getCommentContents());
        commentDto.setCommentWriter(commentEntity.getCommentWriter());
        commentDto.setId(commentEntity.getId());
        commentDto.setBoardId(commentEntity.getBoardEntity().getId());
        commentDto.setCommentCreatedTime(commentEntity.getCreatedDate());
        commentDto.setCommentUpdatedTime(commentEntity.getModifiedDate());
        return commentDto;

    }
}
