package Nova.Post.service;

import Nova.Post.Dto.ReplyDto;
import Nova.Post.domain.CommentEntity;
import Nova.Post.domain.ReplyEntitiy;
import Nova.Post.repository.CommentRepository;
import Nova.Post.repository.ReplyRepository;
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
}
