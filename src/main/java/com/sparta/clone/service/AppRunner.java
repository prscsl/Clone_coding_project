package com.sparta.clone.service;


import com.sparta.clone.domain.Cinema;
import com.sparta.clone.domain.Screening;
import com.sparta.clone.repository.CinemaRepository;
import com.sparta.clone.repository.CrawRepository;
import com.sparta.clone.repository.MemberRepository;
import com.sparta.clone.repository.ScreeningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AppRunner implements ApplicationRunner {
    private final MemberRepository memberRepository;
    private final CinemaRepository cinemaRepository;
    private final CrawRepository crawRepository;
    private final ScreeningRepository screeningRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {

        if(cinemaRepository.count()==0) {
            Cinema cinema = Cinema.builder()
                    .town("춘천")
                    .seats(135)
                    .cinemaSize("I15")
                    .build();
            cinemaRepository.save(cinema);

            Cinema cinema2 = Cinema.builder()
                    .town("강남")
                    .seats(135)
                    .cinemaSize("I15")
                    .build();
            cinemaRepository.save(cinema2);
        }
        //상영표

        if(screeningRepository.count()==0) {
            //15일-춘천-영화 : 1L
            Screening screening1511 = Screening.builder()
                    .movie(crawRepository.findById(1L).get())
                    .cinema(cinemaRepository.findByTown("춘천").get())
                    .date("20220915")
                    .time("09:00")
                    .booked("")
                    .build();
            screeningRepository.save(screening1511);
            Screening screening1512 = Screening.builder()
                    .movie(crawRepository.findById(1L).get())
                    .cinema(cinemaRepository.findByTown("춘천").get())
                    .date("20220915")
                    .time("12:00")
                    .booked("")
                    .build();
            screeningRepository.save(screening1512);
            Screening screening1513 = Screening.builder()
                    .movie(crawRepository.findById(1L).get())
                    .cinema(cinemaRepository.findByTown("춘천").get())
                    .date("20220915")
                    .time("15:00")
                    .booked("")
                    .build();
            screeningRepository.save(screening1513);

            //15일-춘천-영화 : 2L
            Screening screening1521 = Screening.builder()
                    .movie(crawRepository.findById(2L).get())
                    .cinema(cinemaRepository.findByTown("춘천").get())
                    .date("20220915")
                    .time("10:20")
                    .booked("")
                    .build();
            screeningRepository.save(screening1521);
            Screening screening1522 = Screening.builder()
                    .movie(crawRepository.findById(2L).get())
                    .cinema(cinemaRepository.findByTown("춘천").get())
                    .date("20220915")
                    .time("13:30")
                    .booked("")
                    .build();
            screeningRepository.save(screening1522);
            Screening screening1523 = Screening.builder()
                    .movie(crawRepository.findById(2L).get())
                    .cinema(cinemaRepository.findByTown("춘천").get())
                    .date("20220915")
                    .time("17:00")
                    .booked("")
                    .build();
            screeningRepository.save(screening1523);

            //16일-춘천 영화 1L
            Screening screening1611 = Screening.builder()
                    .movie(crawRepository.findById(1L).get())
                    .cinema(cinemaRepository.findByTown("춘천").get())
                    .date("20220916")
                    .time("08:00")
                    .booked("")
                    .build();
            screeningRepository.save(screening1611);
            Screening screening1612 = Screening.builder()
                    .movie(crawRepository.findById(1L).get())
                    .cinema(cinemaRepository.findByTown("춘천").get())
                    .date("20220916")
                    .time("13:30")
                    .booked("")
                    .build();
            screeningRepository.save(screening1612);
            Screening screening1613 = Screening.builder()
                    .movie(crawRepository.findById(1L).get())
                    .cinema(cinemaRepository.findByTown("춘천").get())
                    .date("20220916")
                    .time("21:00")
                    .booked("")
                    .build();
            screeningRepository.save(screening1613);


            //16일-춘천-영화 : 2L
            Screening screening1621 = Screening.builder()
                    .movie(crawRepository.findById(2L).get())
                    .cinema(cinemaRepository.findByTown("춘천").get())
                    .date("20220916")
                    .time("10:00")
                    .booked("")
                    .build();
            screeningRepository.save(screening1621);
            Screening screening1622 = Screening.builder()
                    .movie(crawRepository.findById(2L).get())
                    .cinema(cinemaRepository.findByTown("춘천").get())
                    .date("20220916")
                    .time("13:00")
                    .booked("")
                    .build();
            screeningRepository.save(screening1622);
            Screening screening1623 = Screening.builder()
                    .movie(crawRepository.findById(2L).get())
                    .cinema(cinemaRepository.findByTown("춘천").get())
                    .date("20220916")
                    .time("17:00")
                    .booked("")
                    .build();
            screeningRepository.save(screening1623);

            //15일-강남-영화 : 1L
            Screening screening11511 = Screening.builder()
                    .movie(crawRepository.findById(1L).get())
                    .cinema(cinemaRepository.findByTown("강남").get())
                    .date("20220915")
                    .time("09:00")
                    .booked("")
                    .build();
            screeningRepository.save(screening11511);
            Screening screening11512 = Screening.builder()
                    .movie(crawRepository.findById(1L).get())
                    .cinema(cinemaRepository.findByTown("강남").get())
                    .date("20220915")
                    .time("12:20")
                    .booked("")
                    .build();
            screeningRepository.save(screening11512);
            Screening screening11513 = Screening.builder()
                    .movie(crawRepository.findById(1L).get())
                    .cinema(cinemaRepository.findByTown("강남").get())
                    .date("20220915")
                    .time("18:00")
                    .booked("")
                    .build();
            screeningRepository.save(screening11513);

            //15일-춘천-영화 : 2L
            Screening screening11521 = Screening.builder()
                    .movie(crawRepository.findById(2L).get())
                    .cinema(cinemaRepository.findByTown("강남").get())
                    .date("20220915")
                    .time("10:20")
                    .booked("")
                    .build();
            screeningRepository.save(screening1521);
            Screening screening11522 = Screening.builder()
                    .movie(crawRepository.findById(2L).get())
                    .cinema(cinemaRepository.findByTown("강남").get())
                    .date("20220915")
                    .time("13:30")
                    .booked("")
                    .build();
            screeningRepository.save(screening1522);
            Screening screening11523 = Screening.builder()
                    .movie(crawRepository.findById(2L).get())
                    .cinema(cinemaRepository.findByTown("강남").get())
                    .date("20220915")
                    .time("17:00")
                    .booked("")
                    .build();
            screeningRepository.save(screening11523);

            //16일-춘천 영화 1L
            Screening screening11611 = Screening.builder()
                    .movie(crawRepository.findById(1L).get())
                    .cinema(cinemaRepository.findByTown("강남").get())
                    .date("20220916")
                    .time("08:00")
                    .booked("")
                    .build();
            screeningRepository.save(screening11611);
            Screening screening11612 = Screening.builder()
                    .movie(crawRepository.findById(1L).get())
                    .cinema(cinemaRepository.findByTown("강남").get())
                    .date("20220916")
                    .time("13:30")
                    .booked("")
                    .build();
            screeningRepository.save(screening11612);
            Screening screening11613 = Screening.builder()
                    .movie(crawRepository.findById(1L).get())
                    .cinema(cinemaRepository.findByTown("강남").get())
                    .date("20220916")
                    .time("21:00")
                    .booked("")
                    .build();
            screeningRepository.save(screening11613);


            //16일-춘천-영화 : 2L
            Screening screening11621 = Screening.builder()
                    .movie(crawRepository.findById(2L).get())
                    .cinema(cinemaRepository.findByTown("강남").get())
                    .date("20220916")
                    .time("10:00")
                    .booked("")
                    .build();
            screeningRepository.save(screening11621);
            Screening screening11622 = Screening.builder()
                    .movie(crawRepository.findById(2L).get())
                    .cinema(cinemaRepository.findByTown("강남").get())
                    .date("20220916")
                    .time("13:30")
                    .booked("")
                    .build();
            screeningRepository.save(screening11622);
            Screening screening11623 = Screening.builder()
                    .movie(crawRepository.findById(2L).get())
                    .cinema(cinemaRepository.findByTown("강남").get())
                    .date("20220916")
                    .time("22:00")
                    .booked("")
                    .build();
            screeningRepository.save(screening11623);
        }

    }
}

