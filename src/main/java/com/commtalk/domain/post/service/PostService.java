package com.commtalk.domain.post.service;

import com.commtalk.domain.post.dto.PostDTO;
import com.commtalk.domain.post.dto.PostPreviewDTO;
import com.commtalk.domain.post.dto.request.PostCreateRequest;
import com.commtalk.domain.post.dto.PostPageDTO;
import com.commtalk.domain.post.dto.request.PostUpdateRequest;
import com.commtalk.domain.post.entity.ActivityType;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {

    PostPageDTO getPosts(Pageable pageable);

    PostPageDTO getPostsByBoard(Long boardId, Pageable pageable);

    PostPageDTO getPostsByKeyword(String keyword, Pageable pageable);

    PostPageDTO getPostsByBoardAndKeyword(Long boardId, String keyword, Pageable pageable);

    PostPageDTO getPostsByAuthor(Long memberId, Pageable pageable);

    PostPageDTO getPostsByIds(List<Long> postIds, Pageable pageable);

    PostDTO getPost(Long postId);

    PostDTO getPost(Long postId, Long memberId);

    void isExistsPost(Long postId);

    List<PostPreviewDTO> getPostPreviewsByBoard(Long boardId, int size);

    List<PostPreviewDTO> getPostPreviewsTop3ByViews();

    Long createPost(Long memberId, Long boardId, PostCreateRequest createReq);

    void updatePost(Long memberId, Long postId, PostUpdateRequest updateReq);

    void deletePost(Long memberId, Long postId);

    int countPostByBoard(Long boardId);

}
