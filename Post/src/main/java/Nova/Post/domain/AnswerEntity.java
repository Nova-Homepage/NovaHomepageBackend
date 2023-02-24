package Nova.Post.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "answer_table")
public class AnswerEntity {
    @Id @GeneratedValue
    @Column(name = "answer_id")
    private Long id;

    @Column
    private  String year;

    @Column
    private  String subject_name;

    @Column
    private String pro_name;

    @OneToOne
    @JoinColumn(name ="post_id")
    BoardEntity boardEntity;




}
