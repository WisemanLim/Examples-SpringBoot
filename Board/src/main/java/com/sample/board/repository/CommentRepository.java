package com.sample.board.repository;

import com.sample.board.model.BoardModel;
import com.sample.board.model.CommentModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentModel, Long> {
    // select * from comment_table where board_id=? order by id desc;
    List<CommentModel> findAllByBoardModelOrderByIdDesc(BoardModel boardModel);
}
