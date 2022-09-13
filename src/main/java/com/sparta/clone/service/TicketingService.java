package com.sparta.clone.service;


import com.sparta.clone.controller.dto.request.TimeAndSeatDto;
import com.sparta.clone.controller.dto.response.ResponseDto;
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
        List<String> cities = new ArrayList<>();
        List<String> towns = new ArrayList<>();
        for (int i = 0; i < temp_cinemas.size(); i++) {
            cities.add(temp_cinemas.get(i).getCity());
            towns.add(temp_cinemas.get(i).getTown());
        }
        TicketingResponseDto responseDto = TicketingResponseDto.builder()
                .movies(movies)
                .cities(cities)
                .towns(towns)
                .build();
        return ResponseDto.success(responseDto);
    }

    // 영화+극장+날짜 받고 시간 조회
    public ResponseDto<?> timeAndSeat(TimeAndSeatDto timeAndSeatDto,HttpServletRequest request) {
        CGVmovie movie = crawRepository.findByTitle(timeAndSeatDto.getTitle()).get(0);
        Cinema cinema = cinemaRepository.findByCityAndTown(timeAndSeatDto.getCity(),timeAndSeatDto.getTown()).get();
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
    public ResponseDto<?> seat(String title, String city, String town, String date, String time){

        return ResponseDto.success("dto");
    }

    //티켓구매

    //티켓 조회
}
