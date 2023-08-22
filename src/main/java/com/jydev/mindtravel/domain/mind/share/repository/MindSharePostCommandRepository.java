package com.jydev.mindtravel.domain.mind.share.repository;

import com.jydev.mindtravel.domain.mind.share.domain.MindSharePost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MindSharePostCommandRepository extends JpaRepository<MindSharePost, Long> {
}
