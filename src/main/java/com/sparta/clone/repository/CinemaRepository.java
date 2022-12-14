package com.sparta.clone.repository;

import com.sparta.clone.domain.CGVmovie;
import com.sparta.clone.domain.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CinemaRepository extends JpaRepository<Cinema, String> {
    Optional<Cinema> findByTown(String town);

}
