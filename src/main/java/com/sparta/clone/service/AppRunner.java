//package com.sparta.clone.service;
//
//
//import com.sparta.clone.domain.Cinema;
//import com.sparta.clone.domain.Member;
//import com.sparta.clone.domain.Screening;
//import com.sparta.clone.repository.CinemaRepository;
//import com.sparta.clone.repository.CrawRepository;
//import com.sparta.clone.repository.MemberRepository;
//import com.sparta.clone.repository.ScreeningRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//@Component
//@RequiredArgsConstructor
//public class AppRunner implements ApplicationRunner {
//    private final MemberRepository memberRepository;
//    private final CinemaRepository cinemaRepository;
//    private final CrawRepository crawRepository;
//    private final ScreeningRepository screeningRepository;
//
//    @Override
//    @Transactional
//    public void run(ApplicationArguments args) throws Exception {
//
//        if(cinemaRepository.count()==0) {
//            Cinema cinema = Cinema.builder()
//                    .city("강원")
//                    .town("춘천")
//                    .seats(135)
//                    .cinemaSize("I15")
//                    .build();
//            cinemaRepository.save(cinema);
//        }
//
//        if(screeningRepository.count()==0) {
//            Screening screening11 = Screening.builder()
//                    .movie(crawRepository.findById(1L).get())
//                    .cinema(cinemaRepository.findByCityAndTown("강원","춘천").get())
//                    .date("20220913")
//                    .time("12:00")
//                    .booked("")
//                    .build();
//            screeningRepository.save(screening11);
//            Screening screening21 = Screening.builder()
//                    .movie(crawRepository.findById(1L).get())
//                    .cinema(cinemaRepository.findByCityAndTown("강원","춘천").get())
//                    .date("20220914")
//                    .time("12:00")
//                    .booked("")
//                    .build();
//            screeningRepository.save(screening21);
//            Screening screening31 = Screening.builder()
//                    .movie(crawRepository.findById(1L).get())
//                    .cinema(cinemaRepository.findByCityAndTown("강원","춘천").get())
//                    .date("20220915")
//                    .time("12:00")
//                    .booked("")
//                    .build();
//            screeningRepository.save(screening31);
//            Screening screening12 = Screening.builder()
//                    .movie(crawRepository.findById(1L).get())
//                    .cinema(cinemaRepository.findByCityAndTown("강원","춘천").get())
//                    .date("20220913")
//                    .time("15:00")
//                    .booked("")
//                    .build();
//            screeningRepository.save(screening12);
//            Screening screening22 = Screening.builder()
//                    .movie(crawRepository.findById(1L).get())
//                    .cinema(cinemaRepository.findByCityAndTown("강원","춘천").get())
//                    .date("20220914")
//                    .time("15:00")
//                    .booked("")
//                    .build();
//            screeningRepository.save(screening22);
//            Screening screening32 = Screening.builder()
//                    .movie(crawRepository.findById(1L).get())
//                    .cinema(cinemaRepository.findByCityAndTown("강원","춘천").get())
//                    .date("20220915")
//                    .time("15:00")
//                    .booked("")
//                    .build();
//            screeningRepository.save(screening32);
//        }
//
//    }
//}
