package com.commtalk.domain.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "게시글 댓글 생성 정보")
public class CreateCommentDTO {

    @Schema(description = "상위 댓글 식별자")
    private Long parentId;

    @NotBlank(message = "댓글 내용은 필수 입력 값입니다.")
    @Schema(description = "댓글 내용")
    private String content;
    
    @Schema(description = "익명 여부")
    private boolean anonymousYN;
    
}