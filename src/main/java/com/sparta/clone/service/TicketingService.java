package com.sparta.clone.service;


import com.sparta.clone.controller.dto.request.MovieAndCinemaRequestDto;
import com.sparta.clone.controller.dto.request.SelectSeatRequestDto;
import com.sparta.clone.controller.dto.request.TimeAndSeatRequestDto;
import com.sparta.clone.controller.dto.response.*;
import com.sparta.clone.domain.*;
import com.sparta.clone.repository.CinemaRepository;
import com.sparta.clone.repository.CrawRepository;
import com.sparta.clone.repository.ScreeningRepository;
import com.sparta.clone.repository.TicketingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class TicketingService {

    private final CrawRepository crawRepository;
    private final CinemaRepository cinemaRepository;
    private final ScreeningRepository screeningRepository;
    private final UtilService utilService;
    private final TicketingRepository ticketingRepository;
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
    public ResponseDto<?> timeAndSeat(MovieAndCinemaRequestDto movieAndCinemaRequestDto, HttpServletRequest request) {
        CGVmovie movie = crawRepository.findByTitle(movieAndCinemaRequestDto.getTitle()).get(0);
        Cinema cinema = cinemaRepository.findByTown(movieAndCinemaRequestDto.getTown()).get();
        List<Screening> screenings = screeningRepository.findByMovieAndCinema(movie,cinema);
        List<TimeAndSeatResponseDto> responseDtos=new ArrayList<>();

        for (int i = 0; i < screenings.size(); i++) {
            if(screenings.get(i).getDate().equals(movieAndCinemaRequestDto.getDate())){
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
    public ResponseDto<?> seat(TimeAndSeatRequestDto timeAndSeatRequestDto, HttpServletRequest request){
        CGVmovie movie = crawRepository.findByTitle(timeAndSeatRequestDto.getTitle()).get(0);
        Cinema cinema = cinemaRepository.findByTown(timeAndSeatRequestDto.getTown()).get();
        List<Screening> screenings = screeningRepository.findByMovieAndCinema(movie,cinema);
        Screening temp_screening = new Screening();
        Screening screening = new Screening();
        ArrayList<Screening> temp_screenings = new ArrayList<>();

        for (int i = 0; i < screenings.size(); i++) {
            if(screenings.get(i).getDate().equals(timeAndSeatRequestDto.getDate())){
                //1차 캐시인지 확인 필요
                temp_screening=screenings.get(i);
                temp_screenings.add(temp_screening);
            }
        }
        for (int i = 0; i < temp_screenings.size(); i++) {
            if(temp_screenings.get(i).getTime().equals(timeAndSeatRequestDto.getTime())){
                screening=temp_screenings.get(i);
            }
        }
        String temp_seat=screening.getBooked();

        ArrayList<String> temp_stringList=new ArrayList<>();
        SeatAtoIDto seatAtoIDto=new SeatAtoIDto(temp_stringList,temp_stringList,temp_stringList,temp_stringList,temp_stringList,temp_stringList,temp_stringList,temp_stringList,temp_stringList);

        if(temp_seat!=null) {
            seatAtoIDto = stringToSeatAtoIDto(temp_seat);
        }
        SeatResponseDto seatResponseDto= SeatResponseDto.builder()
                .maxSeat(screening.getCinema().getSeats()) //최대좌석 수
                .remainingSeat(screening.getCinema().getSeats()-temp_seat.split(",").length) //남은좌석 수
                .seat(seatAtoIDto)
                .build();

        return ResponseDto.success(seatResponseDto);
    }

    @Transactional
    public ResponseDto<?> seatTest(TimeAndSeatRequestDto timeAndSeatRequestDto, HttpServletRequest request){
        CGVmovie movie = crawRepository.findByTitle(timeAndSeatRequestDto.getTitle()).get(0);
        Cinema cinema = cinemaRepository.findByTown(timeAndSeatRequestDto.getTown()).get();
        List<Screening> screenings = screeningRepository.findByMovieAndCinema(movie,cinema);
        Screening temp_screening = new Screening();
        Screening screening = new Screening();
        ArrayList<Screening> temp_screenings = new ArrayList<>();

        for (int i = 0; i < screenings.size(); i++) {
            if(screenings.get(i).getDate().equals(timeAndSeatRequestDto.getDate())){
                //1차 캐시인지 확인 필요
                temp_screening=screenings.get(i);
                temp_screenings.add(temp_screening);
            }
        }
        for (int i = 0; i < temp_screenings.size(); i++) {
            if(temp_screenings.get(i).getTime().equals(timeAndSeatRequestDto.getTime())){
                screening=temp_screenings.get(i);
            }
        }
        String temp_seat="A3,B13";

        ArrayList<String> temp_stringList=new ArrayList<>();
        SeatAtoIDto seatAtoIDto=new SeatAtoIDto(temp_stringList,temp_stringList,temp_stringList,temp_stringList,temp_stringList,temp_stringList,temp_stringList,temp_stringList,temp_stringList);

        if(temp_seat!=null) {
            seatAtoIDto = stringToSeatAtoIDto(temp_seat);
        }
        SeatResponseDto seatResponseDto= SeatResponseDto.builder()
                .maxSeat(screening.getCinema().getSeats()) //최대좌석 수
                .remainingSeat(screening.getCinema().getSeats()-temp_seat.split(",").length) //남은좌석 수
                .seat(seatAtoIDto)
                .build();

        return ResponseDto.success(seatResponseDto);
    }

    //티켓 구매
    @Transactional
    public ResponseDto<?> buyTicket(SelectSeatRequestDto selectSeatRequestDto,HttpServletRequest request){
        // 유저정보 조회
        Member member = utilService.loggedInMember(request);
        // 상영 정보 조회
        CGVmovie movie = crawRepository.findByTitle(selectSeatRequestDto.getTitle()).get(0);
        Cinema cinema = cinemaRepository.findByTown(selectSeatRequestDto.getTown()).get();
        List<Screening> screenings = screeningRepository.findByMovieAndCinema(movie,cinema);
        Screening temp_screening = new Screening();
        Screening screening = new Screening();
        ArrayList<Screening> temp_screenings = new ArrayList<>();

        for (int i = 0; i < screenings.size(); i++) {
            if(screenings.get(i).getDate().equals(selectSeatRequestDto.getDate())){
                //1차 캐시인지 확인 필요
                temp_screening=screenings.get(i);
                temp_screenings.add(temp_screening);
            }
        }
        for (int i = 0; i < temp_screenings.size(); i++) {
            if(temp_screenings.get(i).getTime().equals(selectSeatRequestDto.getTime())){
                screening=temp_screenings.get(i);
            }
        }


        // 예매 정보조회
        String seat = selectSeatRequestDto.getSeat();
        String[] seats = seat.split(",");
        int memberCount = seats.length;
        // int memberCount = selectSeatRequestDto.getMemberCount();

        // 구매 가능 좌석인지 확인
        String[] bookedSeats=screening.getBooked().split(",");
        ArrayList<String> nonAvailableSeats=new ArrayList<>();
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < bookedSeats.length; j++) {
                if(seats[i].equals(bookedSeats[j])){
                    nonAvailableSeats.add(seats[i]);
                }
            }
        }
        if(nonAvailableSeats.size()!=0){
            String nonAvailableSeat="";
            for (int i = 0; i < nonAvailableSeats.size(); i++) {
                if(i==0){
                    nonAvailableSeat = nonAvailableSeat+nonAvailableSeats.get(i);
                }
                else {
                    nonAvailableSeat = nonAvailableSeat+","+nonAvailableSeats.get(i);
                }
            }
            String responseMsg="해당 좌석 ("+nonAvailableSeat+")은(는) 이미 예약된 좌석입니다.";
            return ResponseDto.fail("Non Available Seat",responseMsg);
        }


        // 예매 정보 상영 정보에 반영
        System.out.println(screening.getBooked());
        screening.book(seat);

        // 예매 정보 개인 정보에 반영
        Ticketing ticketing = Ticketing.builder()
                .member(member)
                .screening(screening)
                .seat(seat)
                .memberCount(memberCount)
                .build();
        ticketingRepository.save(ticketing);

        return ResponseDto.success("구매완료");
    }


    // Strign형 좌석을 객체로 변환
    public SeatAtoIDto stringToSeatAtoIDto(String book){
        String[] temp_list=book.split(",");
        Pattern patternA = Pattern.compile("^A+[0-9]{0,2}$");
        Pattern patternB = Pattern.compile("^B+[0-9]{0,2}$");
        Pattern patternC = Pattern.compile("^C+[0-9]{0,2}$");
        Pattern patternD = Pattern.compile("^D+[0-9]{0,2}$");
        Pattern patternE = Pattern.compile("^E+[0-9]{0,2}$");
        Pattern patternF = Pattern.compile("^F+[0-9]{0,2}$");
        Pattern patternG = Pattern.compile("^G+[0-9]{0,2}$");
        Pattern patternH = Pattern.compile("^H+[0-9]{0,2}$");
        Pattern patternI = Pattern.compile("^I+[0-9]{0,2}$");

        ArrayList<String> A = new ArrayList<>();ArrayList<String> B = new ArrayList<>();ArrayList<String> C = new ArrayList<>();
        ArrayList<String> D = new ArrayList<>();ArrayList<String> E = new ArrayList<>();ArrayList<String> F = new ArrayList<>();
        ArrayList<String> G = new ArrayList<>();ArrayList<String> H = new ArrayList<>();ArrayList<String> I = new ArrayList<>();

        for (int i = 0; i < temp_list.length; i++) {
            if(patternA.matcher(temp_list[i]).matches()){
                A.add(temp_list[i]);
            }
            if(patternB.matcher(temp_list[i]).matches()){
                B.add(temp_list[i]);
            }
            if(patternC.matcher(temp_list[i]).matches()){
                C.add(temp_list[i]);
            }
            if(patternD.matcher(temp_list[i]).matches()){
                D.add(temp_list[i]);
            }
            if(patternE.matcher(temp_list[i]).matches()){
                E.add(temp_list[i]);
            }
            if(patternF.matcher(temp_list[i]).matches()){
                F.add(temp_list[i]);
            }
            if(patternG.matcher(temp_list[i]).matches()){
                G.add(temp_list[i]);
            }
            if(patternH.matcher(temp_list[i]).matches()){
                H.add(temp_list[i]);
            }
            if(patternI.matcher(temp_list[i]).matches()){
                I.add(temp_list[i]);
            }

        }
        SeatAtoIDto seatAtoIDto = SeatAtoIDto.builder()
                .A(A)
                .B(B)
                .C(C)
                .D(D)
                .E(E)
                .F(F)
                .G(G)
                .H(H)
                .I(I)
                .build();
        return seatAtoIDto;
    }


    //티켓 조회
}
