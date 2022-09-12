package com.sparta.clone.repository;

import com.sparta.clone.domain.Cinema;
import com.sparta.clone.domain.Screening;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreenigRepository extends JpaRepository<Screening, Long> {

}
