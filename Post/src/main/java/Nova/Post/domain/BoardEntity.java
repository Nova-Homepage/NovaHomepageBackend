package Nova.Post.domain;

import Nova.Post.Dto.BoardDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//DTO (Data Transfer Object) 데이터 전송 객체 -> Entity를 가리는것 같다.
@Entity
@Getter @Setter
@NoArgsConstructor //기본생성자
@AllArgsConstructor //모든 필드를 매개변수로 하는 생성자
@Table(name="board_table")
public class BoardEntity extends BaseTimeEntity{ //자바에서 상속받은 부모 클래스도 JPA에서는 한 객체로 인식해서 DATA 테이블에 넣어줌
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    @Column(name ="board_id")
    private Long id;

    @Column
    private String title;

    @Column // default 255 ,null 가능  //length = 20 , nullable = false 로 조건 가능
    private String content;

    @Enumerated(EnumType.STRING) //DB에 저장되는 방식인듯
    private TagState tag;

    @OneToMany(mappedBy ="boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<FileEntity> FileEntityList = new ArrayList<>(); //여러개가 올수 있도록 참조관계 설정 -> db에 저장되는건 아님


    @OneToMany(mappedBy ="boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>(); //여러개가 올수 있도록 참조관계 설정 -> db에 저장되는건 아님

    @OneToOne(mappedBy ="boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private AnswerEntity answerEntity;


    //entitiy -> dto
    public static BoardEntity toSaveEntity(BoardDto boardDto)
    {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setTag(boardDto.getTag());
        boardEntity.setContent(boardDto.getContent());
        boardEntity.setTitle(boardDto.getTitle());

        return boardEntity;

    }

    public static BoardEntity toUpdateEntity(BoardDto boardDto) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setId(boardDto.getId()); //아주 중요!
        boardEntity.setTag(boardDto.getTag());
        boardEntity.setContent(boardDto.getContent());
        boardEntity.setTitle(boardDto.getTitle());
        boardEntity.setModifiedDate(LocalDateTime.now());
//        post.setCreatedDate(postDto.getCreatedDate());
//        post.setModifiedDate(LocalDateTime.now());


        return boardEntity;
    }
}
