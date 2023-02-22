package Nova.Post.service;

import Nova.Post.Dto.CommentDto;
import Nova.Post.Dto.PostDto;
import Nova.Post.Dto.ReplyDto;
import Nova.Post.domain.CommentEntity;
import Nova.Post.domain.Post;
import Nova.Post.domain.ReplyEntitiy;
import Nova.Post.repository.CommentRepository;
import Nova.Post.repository.PostRepository;
import Nova.Post.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ReplyRepository replyRepository;

    //댓글 저장
    public Long save(CommentDto commentDto) {
        Post post = postRepository.findById(commentDto.getPostId()).get(); //부모엔티티 조회
        CommentEntity commentEntity = CommentEntity.toSaveEntity(commentDto,post);
        return commentRepository.save(commentEntity).getId();
        //builder
    }

    //모든 댓글 찾기 댓글 -> 대댓글
    public  List<Map<String,Object>> findAll(Long postId) {
        // select * from comment_table where board_id? order by id desc;
        Post post = postRepository.findById(postId).get();
        List<CommentEntity> commentEntityList = commentRepository.findAllByPostOrderByIdAsc(post);

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
        Post post = postRepository.findById(commentDto.getPostId()).get();

        CommentEntity commentEntity = CommentEntity.toUpdateEntitiy(commentDto, post);
        commentRepository.save(commentEntity);
        return findById(commentDto.getId());
    }

    //댓글삭제
    @Transactional(readOnly = false)
    public void delete(Long id) {
        commentRepository.deleteById(id);
    }
}
