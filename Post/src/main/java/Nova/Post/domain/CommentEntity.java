package Nova.Post.domain;

import Nova.Post.Dto.CommentDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "comment_table")
public class CommentEntity extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="comment_id")
    private Long id;

    @Column(length =20, nullable = false)
    private String commentWriter;

    @Column
    private String commentContents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

    @OneToMany(mappedBy ="commentEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ReplyEntitiy> replyEntitiyList= new ArrayList<>();

    public static CommentEntity toSaveEntity(CommentDto commentDto, BoardEntity boardEntity) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCommentWriter(commentDto.getCommentWriter());
        commentEntity.setCommentContents(commentDto.getCommentContents());
        commentEntity.setBoardEntity(boardEntity); //부모 엔티티 값을 넣어야 참조관계를 인식한다.
        return commentEntity;

    }
    public  static CommentEntity toUpdateEntitiy(CommentDto commentDto, BoardEntity boardEntity)
    {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCommentWriter(commentDto.getCommentWriter());
        commentEntity.setCommentContents(commentDto.getCommentContents());
        commentEntity.setModifiedDate(LocalDateTime.now());
        commentEntity.setId(commentDto.getId()); //id값을 넣어줘야 update될때 찾아서 알아서 업데이트됨
        commentEntity.setBoardEntity(boardEntity); //부모 엔티티 값을 넣어야 참조관계를 인식한다.
        return commentEntity;
    }
}
