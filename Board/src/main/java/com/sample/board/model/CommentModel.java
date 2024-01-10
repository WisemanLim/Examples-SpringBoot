package com.sample.board.model;

import com.sample.board.vo.CommentVO;
import lombok.Getter;
import lombok.Setter;

//import javax.persistence.*;
import jakarta.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "comment_table")
public class CommentModel extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String commentWriter;

    @Column
    private String commentContents;

    /* Board:Comment = 1:N */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardModel boardModel;


    public static CommentModel toSaveEntity(CommentVO commentVO, BoardModel boardModel) {
        CommentModel commentEntity = new CommentModel();
        commentEntity.setCommentWriter(commentVO.getCommentWriter());
        commentEntity.setCommentContents(commentVO.getCommentContents());
        commentEntity.setBoardModel(boardModel);
        return commentEntity;
    }
}
