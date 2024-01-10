package com.sample.board.model;

import lombok.Getter;
import lombok.Setter;

//import javax.persistence.*;
import jakarta.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "board_file_table")
public class BoardFileModel extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String originalFileName;

    @Column
    private String storedFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardModel boardModel;

    public static BoardFileModel toBoardFileModel(BoardModel boardModel, String originalFileName, String storedFileName) {
        BoardFileModel boardFileModel = new BoardFileModel();
        boardFileModel.setOriginalFileName(originalFileName);
        boardFileModel.setStoredFileName(storedFileName);
        boardFileModel.setBoardModel(boardModel);
        return boardFileModel;
    }
}













