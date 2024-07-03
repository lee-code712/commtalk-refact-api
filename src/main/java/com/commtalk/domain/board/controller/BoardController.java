package com.commtalk.domain.board.controller;

import com.commtalk.common.dto.ResponseDTO;
import com.commtalk.domain.board.dto.BoardDTO;
import com.commtalk.domain.board.dto.PinAndUnpinBoardDTO;
import com.commtalk.domain.board.dto.PinnedBoardDTO;
import com.commtalk.domain.board.service.BoardService;
import com.commtalk.domain.post.dto.PostPreviewDTO;
import com.commtalk.domain.post.service.PostService;
import com.commtalk.security.JwtAuthenticationProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "board", description = "게시판 API")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/boards")
public class BoardController {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    private final BoardService boardSvc;
    private final PostService postSvc;

    @Operation(summary = "전체 게시판 조회")
    @GetMapping(path = "")
    public ResponseEntity<List<BoardDTO>> getAllBoard() {
        List<BoardDTO> boardDtoList = boardSvc.getAllBoard();
        return ResponseEntity.ok(boardDtoList);
    }

    @Operation(summary = "게시판 조회")
    @GetMapping(path = "/{boardId}")
    public ResponseEntity<BoardDTO> getBoard(@PathVariable Long boardId) {
        BoardDTO boardDto = boardSvc.getBoard(boardId);
        return ResponseEntity.ok(boardDto);
    }

    @Operation(summary = "핀고정 게시판 조회")
    @GetMapping(path = "/pinned")
    public ResponseEntity<List<PinnedBoardDTO>> getPinnedBoards(HttpServletRequest request) {
        Long memberId = jwtAuthenticationProvider.getMemberId(request);
        List<PinnedBoardDTO> boardDtoList = boardSvc.getPinnedBoards(memberId); // 핀고정 게시판 목록 조회
        boardDtoList.forEach(pb -> pb.setPosts(postSvc.getPostPreviewsByBoard(pb.getBoardId(), 2))); // 핀고정 게시글 미리보기 조회
        return ResponseEntity.ok(boardDtoList);
    }

    @Operation(summary = "게시판 핀고정 및 해제")
    @PostMapping(path = "/pinned")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<List<PinnedBoardDTO>> pinAndUnpinBoards(@RequestBody @Valid PinAndUnpinBoardDTO boardDto,
                                                            HttpServletRequest request) {
        Long memberId = jwtAuthenticationProvider.getMemberId(request);
        boardSvc.unpinBoards(memberId, boardDto.getUnpinBoardIds()); // 게시판 핀고정 해제
        boardSvc.pinBoards(memberId, boardDto.getPinBoardIds()); // 게시판 핀고정

        List<PinnedBoardDTO> pinnedBoardDtoList = boardSvc.getPinnedBoards(memberId); // 변경된 핀고정 게시판 목록 조회
        pinnedBoardDtoList.forEach(pb -> pb.setPosts(postSvc.getPostPreviewsByBoard(pb.getBoardId(), 2))); // 핀고정 게시글 미리보기 조회
        return ResponseEntity.ok(pinnedBoardDtoList);
    }

    @Operation(summary = "핀고정 게시판 순서 변경")
    @PatchMapping(path = "/pinned/reorder")
    public ResponseEntity<ResponseDTO<String>> reorderPinnedBoard(@RequestBody @Valid List<PinnedBoardDTO> boardDtoList,
                                                                  HttpServletRequest request) {
        Long memberId = jwtAuthenticationProvider.getMemberId(request);
        boardSvc.reorderPinnedBoards(memberId, boardDtoList); // 핀고정 게시판 순서 변경
        return ResponseDTO.of(HttpStatus.OK, "핀고정 게시판 순서를 변경했습니다.");
    }

}
