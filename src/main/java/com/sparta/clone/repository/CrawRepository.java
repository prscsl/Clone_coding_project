package com.sparta.clone.repository;

import com.sparta.clone.domain.CGVmovie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CrawRepository extends JpaRepository<CGVmovie, Long> {

    List<CGVmovie> findByTitle(String title);

}
