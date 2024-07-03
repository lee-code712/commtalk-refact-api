package com.commtalk.domain.post.controller;

import com.commtalk.common.dto.ResponseDTO;
import com.commtalk.domain.board.entity.Board;
import com.commtalk.domain.board.service.BoardService;
import com.commtalk.domain.post.dto.CreatePostDTO;
import com.commtalk.domain.post.dto.PostPageDTO;
import com.commtalk.domain.post.service.PostService;
import com.commtalk.security.JwtAuthenticationProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

@Tag(name = "board", description = "게시판 API")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/boards")
public class PostController {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    private final BoardService boardSvc;
    private final PostService postSvc;

    @Operation(summary = "게시글 목록 조회")
    @GetMapping(path = "/{boardId}/posts")
    public ResponseEntity<PostPageDTO> getPosts(@PathVariable Long boardId, @PageableDefault Pageable pageable) {
        boardSvc.getBoard(boardId); // 게시판이 존재하는지 확인
        PostPageDTO postPageDto = postSvc.getPostsByBoard(boardId, pageable); // 게시글 목록 조회
        return ResponseEntity.ok(postPageDto);
    }

    @Operation(summary = "게시글 생성")
    @PostMapping(path = "/{boardId}/posts")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseDTO<String>> createPost(@PathVariable Long boardId, @RequestBody @Valid CreatePostDTO postDto,
                                                  HttpServletRequest request) {
        Long memberId = jwtAuthenticationProvider.getMemberId(request);
        boardSvc.getBoard(boardId); // 게시판이 존재하는지 확인
        postSvc.createPost(memberId, boardId, postDto); // 게시글 생성
        return ResponseDTO.of(HttpStatus.OK, "게시글을 생성했습니다.");
    }

}