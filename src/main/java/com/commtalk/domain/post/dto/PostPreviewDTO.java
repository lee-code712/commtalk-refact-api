package com.commtalk.domain.post.dto;

import com.commtalk.domain.post.entity.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "게시글 미리보기 정보")
public class PostPreviewDTO {

    @Schema(description = "게시글 식별자")
    private Long postId;

    @Schema(description = "게시글 제목")
    private String title;

    @Schema(description = "댓글 수")
    private long commentCnt;

    @Schema(description = "조회 수")
    private long viewCnt;

    @Schema(description = "좋아요 수")
    private long likeCnt;

    public static PostPreviewDTO of(Post post) {
        return PostPreviewDTO.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .commentCnt((post.isCommentableYN()) ? post.getComments().size() : 0)
                .viewCnt(post.getViewCount())
                .likeCnt(post.getLikeCount())
                .build();
    }

}