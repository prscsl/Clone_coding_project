package com.sparta.clone.repository;

import com.sparta.clone.domain.CGVmovieHeart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HeartRepository extends JpaRepository<CGVmovieHeart, Long> {
    Optional<CGVmovieHeart> findByMovieIdAndMemberId(Long movieId, String memberId);

    List<CGVmovieHeart> findByMemberId(String id);
}
