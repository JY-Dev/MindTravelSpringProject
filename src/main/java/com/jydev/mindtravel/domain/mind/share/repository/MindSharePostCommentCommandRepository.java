package com.jydev.mindtravel.domain.mind.share.repository;

import com.jydev.mindtravel.domain.mind.share.domain.MindSharePostComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MindSharePostCommentCommandRepository extends JpaRepository<MindSharePostComment, Long> {
}
