package com.sparta.clone.service;

import com.sparta.clone.domain.Cinema;
import com.sparta.clone.repository.CinemaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CinemaService {
    private final CinemaRepository cinemaRepository;
    // 영화관 등록 (최초 자동등록 + 수동 등록)

    public void cinemaResist(){
        Cinema cinema = Cinema.builder()
                .city("강원")
                .town("춘천")
                .seats(135)
                .cinemaSize("I15")
                .build();
        cinemaRepository.save(cinema);
    }

}
