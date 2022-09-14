package com.sparta.clone.repository;

import com.sparta.clone.domain.CGVmovie;
import com.sparta.clone.domain.Cinema;
import com.sparta.clone.domain.Screening;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScreeningRepository extends JpaRepository<Screening, Long> {

    List<Screening> findByMovieAndCinema(CGVmovie movie, Cinema cinema);
}
