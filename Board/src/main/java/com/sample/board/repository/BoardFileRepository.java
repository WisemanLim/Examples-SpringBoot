package com.sample.board.repository;

import com.sample.board.model.BoardFileModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardFileRepository extends JpaRepository<BoardFileModel, Long> {
}
