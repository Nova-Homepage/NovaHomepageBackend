package com.board.novaapi.service;


import com.board.novaapi.dto.boarddto.CommentDto;
import com.board.novaapi.dto.boarddto.ReplyDto;
import com.board.novaapi.entity.board.BoardEntity;
import com.board.novaapi.entity.board.CommentEntity;
import com.board.novaapi.entity.board.ReplyEntitiy;
import com.board.novaapi.repository.board.BoardRepository;
import com.board.novaapi.repository.board.CommentRepository;
import com.board.novaapi.repository.board.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository postRepository;
    private final ReplyRepository replyRepository;

    //댓글 저장
    public Long save(CommentDto commentDto) {
        BoardEntity boardEntity = postRepository.findById(commentDto.getBoardId()).get(); //부모엔티티 조회
        CommentEntity commentEntity = CommentEntity.toSaveEntity(commentDto, boardEntity);
        return commentRepository.save(commentEntity).getId();
        //builder
    }

    //모든 댓글 찾기 댓글 -> 대댓글
    public  List<Map<String,Object>> findAll(Long postId) {
        // select * from comment_table where board_id? order by id desc;
        BoardEntity boardEntity = postRepository.findById(postId).get();
        List<CommentEntity> commentEntityList = commentRepository.findAllByBoardEntityOrderByIdAsc(boardEntity);

        List<Map<String,Object>> commentDtoList = new ArrayList<>();
        for( CommentEntity commentEntity : commentEntityList)
        {
            List<ReplyEntitiy> replyEntitiyList = replyRepository.findAllByCommentEntityOrderByIdAsc(commentEntity);
            Map<String, Object> result = new HashMap<>();
            CommentDto commentDto = CommentDto.toCommentDto(commentEntity);
            List<ReplyDto> replyDtoList = new ArrayList<>();

            for(ReplyEntitiy replyEntitiy : replyEntitiyList)
            {
                replyDtoList.add(ReplyDto.toReplyDto(replyEntitiy));
            }

            result.put("parentComment",commentDto);
            result.put("childComment",replyDtoList);
            commentDtoList.add(result);
        }

        return commentDtoList;
    }
    //댓글 하나 찾기
    @Transactional(readOnly = false)
    public CommentDto findById(Long id)
    {
        Optional<CommentEntity> Optionalcomment = commentRepository.findById(id);
        if(Optionalcomment.isPresent()) {
            CommentEntity commentEntity = Optionalcomment.get();
            CommentDto commentDto = CommentDto.toCommentDto(commentEntity);
            return commentDto;
        }
        else {
            return null;
        }
    }
    //댓글 수정
    @Transactional(readOnly = false)
    public CommentDto update(CommentDto commentDto) {
        BoardEntity boardEntity = postRepository.findById(commentDto.getBoardId()).get();

        CommentEntity commentEntity = CommentEntity.toUpdateEntitiy(commentDto, boardEntity);
        commentRepository.save(commentEntity);
        return findById(commentDto.getId());
    }

    //댓글삭제
    @Transactional(readOnly = false)
    public void delete(Long id) {
        commentRepository.deleteById(id);
    }
}
