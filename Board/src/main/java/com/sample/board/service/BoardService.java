package com.sample.board.service;

import com.sample.board.vo.BoardVO;
import com.sample.board.model.BoardModel;
import com.sample.board.model.BoardFileModel;
import com.sample.board.repository.BoardFileRepository;
import com.sample.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// DTO -> Entity (Entity Class)
// Entity -> DTO (DTO Class)

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;
    public void save(BoardVO boardVO) throws IOException {
        // 파일 첨부 여부에 따라 로직 분리
        if (boardVO.getBoardFile().isEmpty()) {
            // 첨부 파일 없음.
            BoardModel boardModel = BoardModel.toSaveModel(boardVO);
            boardRepository.save(boardModel);
        } else {
            // 첨부 파일 있음.
            /*
                1. DTO에 담긴 파일을 꺼냄
                2. 파일의 이름 가져옴
                3. 서버 저장용 이름을 만듦
                // 내사진.jpg => 839798375892_내사진.jpg
                4. 저장 경로 설정
                5. 해당 경로에 파일 저장
                6. board_table에 해당 데이터 save 처리
                7. board_file_table에 해당 데이터 save 처리
             */
            MultipartFile boardFile = boardVO.getBoardFile(); // 1.
            String originalFilename = boardFile.getOriginalFilename(); // 2.
            String storedFileName = System.currentTimeMillis() + "_" + originalFilename; // 3.
            String savePath = "C:/springboot_img/" + storedFileName; // 4. C:/springboot_img/9802398403948_내사진.jpg
//            String savePath = "/Users/사용자이름/springboot_img/" + storedFileName; // C:/springboot_img/9802398403948_내사진.jpg
            boardFile.transferTo(new File(savePath)); // 5.
            BoardModel boardModel = BoardModel.toSaveFileModel(boardVO);
            Long savedId = boardRepository.save(boardModel).getId();
            BoardModel board = boardRepository.findById(savedId).get();

            BoardFileModel boardFileModel = BoardFileModel.toBoardFileModel(board, originalFilename, storedFileName);
            boardFileRepository.save(boardFileModel);
        }

    }

    @Transactional
    public List<BoardVO> findAll() {
        List<BoardModel> boardModelList = boardRepository.findAll();
        List<BoardVO> boardVOList = new ArrayList<>();
        for (BoardModel boardModel: boardModelList) {
            boardVOList.add(BoardVO.toBoardVO(boardModel));
        }
        return boardVOList;
    }

    @Transactional
    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    @Transactional
    public BoardVO findById(Long id) {
        Optional<BoardModel> optionalBoardModel = boardRepository.findById(id);
        if (optionalBoardModel.isPresent()) {
            BoardModel boardModel = optionalBoardModel.get();
            BoardVO boardVO = BoardVO.toBoardVO(boardModel);
            return boardVO;
        } else {
            return null;
        }
    }

    public BoardVO update(BoardVO boardVO) {
        BoardModel boardModel = BoardModel.toUpdateModel(boardVO);
        boardRepository.save(boardModel);
        return findById(boardVO.getId());
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    public Page<BoardVO> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 3; // 한 페이지에 보여줄 글 갯수
        // 한페이지당 3개씩 글을 보여주고 정렬 기준은 id 기준으로 내림차순 정렬
        // page 위치에 있는 값은 0부터 시작
        Page<BoardModel> boardModels =
                boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        System.out.println("boardModels.getContent() = " + boardModels.getContent()); // 요청 페이지에 해당하는 글
        System.out.println("boardModels.getTotalElements() = " + boardModels.getTotalElements()); // 전체 글갯수
        System.out.println("boardModels.getNumber() = " + boardModels.getNumber()); // DB로 요청한 페이지 번호
        System.out.println("boardModels.getTotalPages() = " + boardModels.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardModels.getSize() = " + boardModels.getSize()); // 한 페이지에 보여지는 글 갯수
        System.out.println("boardModels.hasPrevious() = " + boardModels.hasPrevious()); // 이전 페이지 존재 여부
        System.out.println("boardModels.isFirst() = " + boardModels.isFirst()); // 첫 페이지 여부
        System.out.println("boardModels.isLast() = " + boardModels.isLast()); // 마지막 페이지 여부

        // 목록: id, writer, title, hits, createdTime
        Page<BoardVO> boardVOS = boardModels.map(board -> new BoardVO(board.getId(), board.getBoardWriter(), board.getBoardTitle(), board.getBoardHits(), board.getCreatedTime()));
        return boardVOS;
    }
}














