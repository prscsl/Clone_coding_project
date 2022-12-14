package com.sparta.clone.service;

import com.sparta.clone.domain.Cinema;
import com.sparta.clone.repository.CinemaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class CinemaService {
    private final CinemaRepository cinemaRepository;
    // 영화관 등록 (최초 자동등록 + 수동 등록)


}
