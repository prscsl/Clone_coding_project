package com.sparta.clone.service;


import com.sparta.clone.controller.dto.request.TimeAndSeatDto;
import com.sparta.clone.controller.dto.request.BuyTicketRequestDto;
import com.sparta.clone.controller.dto.response.ResponseDto;
import com.sparta.clone.controller.dto.response.SeatResponseDto;
import com.sparta.clone.controller.dto.response.TicketingResponseDto;
import com.sparta.clone.controller.dto.response.TimeAndSeatResponseDto;
import com.sparta.clone.domain.CGVmovie;
import com.sparta.clone.domain.Cinema;
import com.sparta.clone.domain.Screening;
import com.sparta.clone.repository.CinemaRepository;
import com.sparta.clone.repository.CrawRepository;
import com.sparta.clone.repository.ScreeningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TicketingService {

    private final CrawRepository crawRepository;
    private final CinemaRepository cinemaRepository;
    private final ScreeningRepository screeningRepository;
    //영화 극장 조회
    public ResponseDto<?> movieAndCinema(HttpServletRequest request){

        // 영화 조회

        List<CGVmovie> temp_movies=crawRepository.findAllByStatus(2);
        List<String> movies = new ArrayList<>();
        for (int i = 0; i < temp_movies.size(); i++) {
            movies.add(temp_movies.get(i).getTitle());
        }

        // 극장 조회
        List<Cinema> temp_cinemas = cinemaRepository.findAll();
        List<String> towns = new ArrayList<>();
        for (int i = 0; i < temp_cinemas.size(); i++) {
            towns.add(temp_cinemas.get(i).getTown());
        }
        TicketingResponseDto responseDto = TicketingResponseDto.builder()
                .movies(movies)
                .towns(towns)
                .build();
        return ResponseDto.success(responseDto);
    }

    // 영화+극장+날짜 받고 시간 조회
    public ResponseDto<?> timeAndSeat(TimeAndSeatDto timeAndSeatDto,HttpServletRequest request) {
        CGVmovie movie = crawRepository.findByTitle(timeAndSeatDto.getTitle()).get(0);
        Cinema cinema = cinemaRepository.findByTown(timeAndSeatDto.getTown()).get();
        List<Screening> screenings = screeningRepository.findByMovieAndCinema(movie,cinema);
        List<TimeAndSeatResponseDto> responseDtos=new ArrayList<>();

        for (int i = 0; i < screenings.size(); i++) {
            if(screenings.get(i).getDate().equals(timeAndSeatDto.getDate())){
                TimeAndSeatResponseDto timeAndSeatResponseDto = TimeAndSeatResponseDto.builder()
                        .time(screenings.get(i).getTime())
                        .seat(screenings.get(i).getCinema().getSeats()-screenings.get(i).getBooked().split(",").length)
                        .build();
                responseDtos.add(timeAndSeatResponseDto);
            }
        }
        return ResponseDto.success(responseDtos);
    }

    //좌석 조회
    @Transactional
    public ResponseDto<?> seat(BuyTicketRequestDto buyTicketRequestDto, HttpServletRequest request){
        CGVmovie movie = crawRepository.findByTitle(buyTicketRequestDto.getTitle()).get(0);
        Cinema cinema = cinemaRepository.findByTown(buyTicketRequestDto.getTown()).get();
        List<Screening> screenings = screeningRepository.findByMovieAndCinema(movie,cinema);
        Screening screening = new Screening();

        for (int i = 0; i < screenings.size(); i++) {
            if(screenings.get(i).getDate().equals(buyTicketRequestDto.getTime())){
                //1차 캐시인지 확인 필요
                screening=screenings.get(i);
            }
        }
        screening.getBooked();
        SeatResponseDto seatResponseDto= SeatResponseDto.builder()
                .maxSeat(screening.getCinema().getSeats())
                .remainingSeat(screening.getCinema().getSeats())
                .seatTypeString(screening.getBooked())
                .seatTypeList(screening.getBooked().split("/"))
                .build();

        return ResponseDto.success(seatResponseDto);
    }

    //티켓구매
    public ResponseDto<?> buyTicket(){
        return ResponseDto.success("구매완료");
    }

    //티켓 조회
}
