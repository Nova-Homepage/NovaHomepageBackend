package com.board.novaapi.repository.board;


import com.board.novaapi.entity.board.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity,Long> {
}
