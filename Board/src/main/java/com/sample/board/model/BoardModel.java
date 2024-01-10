package com.sample.board.model;

import com.sample.board.vo.BoardVO;
import lombok.Getter;
import lombok.Setter;

//import javax.persistence.*;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

// DB의 테이블 역할을 하는 클래스
@Entity
@Getter
@Setter
@Table(name = "board_table")
public class BoardModel extends BaseModel {
    @Id // pk 컬럼 지정. 필수
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @Column(length = 20, nullable = false) // 크기 20, not null
    private String boardWriter;

    @Column // 크기 255, null 가능
    private String boardPass;

    @Column
    private String boardTitle;

    @Column(length = 500)
    private String boardContents;

    @Column
    private int boardHits;

    @Column
    private int fileAttached; // 1 or 0

    @OneToMany(mappedBy = "boardModel", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoardFileModel> boardFileModelList = new ArrayList<>();

    @OneToMany(mappedBy = "boardModel", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentModel> commentModelList = new ArrayList<>();

    public static BoardModel toSaveModel(BoardVO boardVO) {
        BoardModel boardModel = new BoardModel();
        boardModel.setBoardWriter(boardVO.getBoardWriter());
        boardModel.setBoardPass(boardVO.getBoardPass());
        boardModel.setBoardTitle(boardVO.getBoardTitle());
        boardModel.setBoardContents(boardVO.getBoardContents());
        boardModel.setBoardHits(0);
        boardModel.setFileAttached(0); // 파일 없음.
        return boardModel;
    }

    public static BoardModel toUpdateModel(BoardVO boardVO) {
        BoardModel boardModel = new BoardModel();
        boardModel.setId(boardVO.getId());
        boardModel.setBoardWriter(boardVO.getBoardWriter());
        boardModel.setBoardPass(boardVO.getBoardPass());
        boardModel.setBoardTitle(boardVO.getBoardTitle());
        boardModel.setBoardContents(boardVO.getBoardContents());
        boardModel.setBoardHits(boardVO.getBoardHits());
        return boardModel;
    }

    public static BoardModel toSaveFileModel(BoardVO boardVO) {
        BoardModel boardModel = new BoardModel();
        boardModel.setBoardWriter(boardVO.getBoardWriter());
        boardModel.setBoardPass(boardVO.getBoardPass());
        boardModel.setBoardTitle(boardVO.getBoardTitle());
        boardModel.setBoardContents(boardVO.getBoardContents());
        boardModel.setBoardHits(0);
        boardModel.setFileAttached(1); // 파일 있음.
        return boardModel;
    }
}











