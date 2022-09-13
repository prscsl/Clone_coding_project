package com.sparta.clone.service;

import com.sparta.clone.domain.Cinema;
import com.sparta.clone.domain.Screening;
import com.sparta.clone.repository.CinemaRepository;
import com.sparta.clone.repository.CrawRepository;
import com.sparta.clone.repository.ScreeningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class ScreeningService {
    private final ScreeningRepository screeningRepository;
    private final CrawRepository crawRepository;
    private final CinemaRepository cinemaRepository;

    // 등록

}
