package Nova.Post.domain;

import Nova.Post.Dto.ReplyDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "reply_table")
public class ReplyEntitiy extends BaseTimeEntity{
    @Id @GeneratedValue
    @Column(name="reply_id")
    private Long id;

    @Column(length =20, nullable = false)
    private String commentWriter;

    @Column
    private String commentContents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private CommentEntity commentEntity;

    public static ReplyEntitiy toSaveEntitiy(ReplyDto replyDto, CommentEntity commentEntity)
    {
        ReplyEntitiy replyEntitiy = new ReplyEntitiy();
        replyEntitiy.setCommentWriter(replyDto.getCommentWriter());
        replyEntitiy.setCommentContents(replyDto.getCommentContents());
        replyEntitiy.setCommentEntity(commentEntity);
        return replyEntitiy;
    }
    public static ReplyEntitiy toUpdateEntity(ReplyDto replyDto,CommentEntity commentEntity)
    {
        ReplyEntitiy replyEntitiy = new ReplyEntitiy();
        replyEntitiy.setCommentWriter(replyDto.getCommentWriter());
        replyEntitiy.setCommentContents(replyDto.getCommentContents());
        replyEntitiy.setCommentEntity(commentEntity);
        replyEntitiy.setModifiedDate(LocalDateTime.now());
        replyEntitiy.setId(replyDto.getId());
        return replyEntitiy;
    }

}
