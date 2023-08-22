package com.jydev.mindtravel.domain.mind.share.repository;

import com.jydev.mindtravel.domain.mind.share.domain.MindSharePostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MindSharePostLikeCommandRepository extends JpaRepository<MindSharePostLike, Long> {
}
