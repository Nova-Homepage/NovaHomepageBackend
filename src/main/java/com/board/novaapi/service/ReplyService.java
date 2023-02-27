package com.board.novaapi.service;


import com.board.novaapi.dto.boarddto.ReplyDto;
import com.board.novaapi.entity.board.CommentEntity;
import com.board.novaapi.entity.board.ReplyEntitiy;
import com.board.novaapi.repository.board.CommentRepository;
import com.board.novaapi.repository.board.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor //생성자를 자동으로 생성해서 의존관계를 주입해줌?
public class ReplyService {

    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;
    @Transactional(readOnly = false)
    public Long save(ReplyDto replyDto) {
        CommentEntity commentEntity = commentRepository.findById(replyDto.getCommenttId()).get();
        ReplyEntitiy replyEntitiy = ReplyEntitiy.toSaveEntitiy(replyDto, commentEntity);
        ReplyEntitiy save = replyRepository.save(replyEntitiy);
        return save.getId();
    }

    public List<ReplyDto> findAll(Long commentId)
    {
        CommentEntity commentEntity = commentRepository.findById(commentId).get();
        List<ReplyEntitiy> replyEntitiylists = replyRepository.findAllByCommentEntityOrderByIdAsc(commentEntity);
        List<ReplyDto> replyDtoList = new ArrayList<>();
        for(ReplyEntitiy replyEntitiy : replyEntitiylists)
        {
             replyDtoList.add(ReplyDto.toReplyDto(replyEntitiy));
        }
        return replyDtoList;
    }
    public ReplyDto findById(Long id)
    {
        Optional<ReplyEntitiy> Optinalreply = replyRepository.findById(id);
        if(Optinalreply.isPresent()) {
            ReplyEntitiy replyEntitiy = Optinalreply.get();
            ReplyDto replyDto = ReplyDto.toReplyDto(replyEntitiy);
            return replyDto;
        }
        else {
            return null;
        }
    }
    @Transactional(readOnly = false)
    public ReplyDto update(ReplyDto replyDto) {
        CommentEntity commentEntity = commentRepository.findById(replyDto.getCommenttId()).get();
        ReplyEntitiy replyEntitiy = ReplyEntitiy.toUpdateEntity(replyDto, commentEntity);
        replyRepository.save(replyEntitiy);
        return findById(replyDto.getId());

    }

    @Transactional(readOnly = false)
    public void delete(Long id) {
        replyRepository.deleteById(id);
    }
}
