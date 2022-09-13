package com.sparta.clone.repository;

import com.sparta.clone.domain.CGVmovie;
import com.sparta.clone.domain.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CinemaRepository extends JpaRepository<Cinema, Long> {

}
