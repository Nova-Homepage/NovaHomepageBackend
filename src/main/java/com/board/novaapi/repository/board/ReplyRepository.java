package com.board.novaapi.repository.board;


import com.board.novaapi.entity.board.CommentEntity;
import com.board.novaapi.entity.board.ReplyEntitiy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<ReplyEntitiy,Long> {
    List<ReplyEntitiy> findAllByCommentEntityOrderByIdAsc(CommentEntity commentEntity);
}
