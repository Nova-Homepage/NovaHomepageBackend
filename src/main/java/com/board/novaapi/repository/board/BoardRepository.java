package com.board.novaapi.repository.board;


import com.board.novaapi.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<User, Long> {
}
