package Nova.Post.repository;

import Nova.Post.domain.CommentEntity;
import Nova.Post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity,Long> {
    // select * from comment_table where board_id? order by id desc; 이런식으로 해야 쿼리가 날라간다
    // 대소문자를 중요하게 작성해야됨
    List<CommentEntity> findAllByPostOrderByIdAsc(Post post);
}
