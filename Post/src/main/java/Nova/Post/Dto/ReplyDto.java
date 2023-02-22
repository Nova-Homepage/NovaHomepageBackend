package Nova.Post.Dto;

import Nova.Post.domain.CommentEntity;
import Nova.Post.domain.ReplyEntitiy;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ReplyDto {
    private Long id;
    private String commentWriter;
    private String commentContents;
    private Long commenttId;
    private LocalDateTime commentCreatedTime;
    private LocalDateTime commentUpdatedTime;

    public static ReplyDto toReplyDto(ReplyEntitiy replyEntitiy) {
        ReplyDto replyDto = new ReplyDto();
        replyDto.setCommentContents(replyEntitiy.getCommentContents());
        replyDto.setCommentWriter(replyEntitiy.getCommentWriter());
        replyDto.setId(replyEntitiy.getId());
        replyDto.setCommenttId(replyEntitiy.getCommentEntity().getId());
        replyDto.setCommentCreatedTime(replyEntitiy.getCreatedDate());
        replyDto.setCommentUpdatedTime(replyEntitiy.getModifiedDate());
        return replyDto;

    }
}
