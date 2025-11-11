package com.example.devSns.Heart;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {

    Boolean existsByPostIdAndMemberId(Long postId, Long memberId);
    Optional<Heart> findByPostIdAndMemberId(Long postId, Long memberId);
    long countByPostIdAndLike(Long postId, LikeStatus like);
}
