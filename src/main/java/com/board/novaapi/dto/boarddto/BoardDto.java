package com.board.novaapi.dto.boarddto;


import com.board.novaapi.entity.board.BoardEntity;
import com.board.novaapi.entity.board.TagState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
@Getter
@Setter
@ToString
@NoArgsConstructor // 기본생성자
// @AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
public class BoardDto {
    private Long id;
    private String title;
    private String content;
    private TagState tag;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;


    public BoardDto(Long id, String title, String content, TagState tag, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.tag = tag;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    //entitiy -> dto
    public static BoardDto toBoardDto(BoardEntity boardEntity)
    {
        BoardDto boardDto = new BoardDto();
        boardDto.setId(boardEntity.getId());
        boardDto.setTag(boardEntity.getTag());
        boardDto.setContent(boardEntity.getContent());
        boardDto.setTitle(boardEntity.getTitle());
        boardDto.setCreatedDate(boardEntity.getCreatedDate());
        boardDto.setModifiedDate(boardEntity.getModifiedDate());

        return boardDto;
    }
}
