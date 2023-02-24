package Nova.Post.repository;

import Nova.Post.domain.CommentEntity;
import Nova.Post.domain.ReplyEntitiy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<ReplyEntitiy,Long> {
    List<ReplyEntitiy> findAllByCommentEntityOrderByIdAsc(CommentEntity commentEntity);
}
