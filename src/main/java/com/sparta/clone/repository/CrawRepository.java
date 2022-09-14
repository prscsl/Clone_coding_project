package com.sparta.clone.repository;

import com.sparta.clone.domain.CGVmovie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CrawRepository extends JpaRepository<CGVmovie, Long> {

    Optional<CGVmovie> findByTitleEng(String titleEng);

    List<CGVmovie> findByTitle(String title);



    List<CGVmovie> findAllByStatus(int Status);
}
