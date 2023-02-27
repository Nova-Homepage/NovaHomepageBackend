package com.board.novaapi.repository.board;


import com.board.novaapi.entity.board.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity,Long> {

    //@Modifying -> update 나 delete를 사용하고싶으면 해당 어노테이션을 붙인다 -> 이런걸 쓰게되면 해당 함수를 사용하려고 하면 트랜잭션을 따로 선언해줘야한다. -> 영속성 컨텍스트를 처리하는것
    // @Query(value = "update BoardEntity b set b.boardHits=boardHits+1 where b.id=:id")
    //void updateHits(@Param("id") Long id)

}
