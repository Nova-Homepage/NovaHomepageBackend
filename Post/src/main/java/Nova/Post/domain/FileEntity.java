package Nova.Post.domain;

import Nova.Post.Dto.FileDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "file_table")
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String originalFileName;

    @Column
    private String storedFileName;

    @Column
    private String file_path;

    @Column
    private String file_type;

    @Column
    private Long file_size;

    @ManyToOne(fetch = FetchType.LAZY) //Eager가 기본 패치전략
    @JoinColumn(name="board_id")
    private BoardEntity boardEntity; //반드시 부모엔티티가 들어가야한다.


    public static FileEntity toSaveFileEntity(FileDto fileDto)
    {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFile_path(fileDto.getFile_path());
        fileEntity.setFile_type(fileDto.getFile_type());
        fileEntity.setFile_size(fileDto.getFile_size());
        fileEntity.setOriginalFileName(fileDto.getOriginalFileName());
        fileEntity.setStoredFileName(fileDto.getStoredFileName());
        fileEntity.setBoardEntity(fileDto.getBoardEntity()); //저장된 객체에 따라서 jpa에 들어갈때 연결시켜주는듯?
        return fileEntity;

    }
}
