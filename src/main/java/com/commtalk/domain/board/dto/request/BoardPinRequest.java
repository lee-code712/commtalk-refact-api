package com.commtalk.domain.board.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Schema(description = "게시판 핀고정 및 해제 정보")
public class BoardPinRequest {

    @Schema(description = "고정할 게시판 식별자 목록")
    private List<Long> pinBoardIds;

    @Schema(description = "고정 해제할 게시판 식별자 목록")
    private List<Long> unpinBoardIds;

}