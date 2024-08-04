package com.commtalk.domain.post.service;

import com.commtalk.domain.post.entity.ActivityType;

import java.util.Map;

public interface MemberActivityService {

    Map<String, Boolean> getMemberActivitiesByPost(Long memberId, Long postId);

    Map<String, Boolean> getMemberActivitiesByComment(Long memberId, Long commentId);

    void doActivity(ActivityType.TypeName typeName, Long memberId, Long refId);

    void undoActivity(ActivityType.TypeName typeName, Long memberId, Long refId);

}
