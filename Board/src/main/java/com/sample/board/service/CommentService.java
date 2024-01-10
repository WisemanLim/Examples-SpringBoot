package com.sample.board.service;

import com.sample.board.vo.CommentVO;
import com.sample.board.model.BoardModel;
import com.sample.board.model.CommentModel;
import com.sample.board.repository.BoardRepository;
import com.sample.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public Long save(CommentVO commentVO) {
        /* 부모엔티티(BoardEntity) 조회 */
        Optional<BoardModel> optionalBoardModel = boardRepository.findById(commentVO.getBoardId());
        if (optionalBoardModel.isPresent()) {
            BoardModel boardModel = optionalBoardModel.get();
            CommentModel commentModel = CommentModel.toSaveEntity(commentVO, boardModel);
            return commentRepository.save(commentModel).getId();
        } else {
            return null;
        }
    }

    public List<CommentVO> findAll(Long boardId) {
        BoardModel boardModel = boardRepository.findById(boardId).get();
        List<CommentModel> commentModelList = commentRepository.findAllByBoardModelOrderByIdDesc(boardModel);
        /* EntityList -> DTOList */
        List<CommentVO> commentVOList = new ArrayList<>();
        for (CommentModel commentModel: commentModelList) {
            CommentVO commentVO = CommentVO.toCommentVO(commentModel, boardId);
            commentVOList.add(commentVO);
        }
        return commentVOList;
    }

}
