package com.sparta.clone.service;

import com.sparta.clone.controller.dto.response.*;
import com.sparta.clone.domain.CGVmovie;
import com.sparta.clone.domain.CGVmovieHeart;
import com.sparta.clone.domain.Member;
import com.sparta.clone.domain.Ticketing;
import com.sparta.clone.repository.CrawRepository;
import com.sparta.clone.repository.HeartRepository;
import com.sparta.clone.repository.TicketingRepository;
import com.sparta.clone.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UtilService utilService;
    private final JwtTokenProvider jwtTokenProvider;
    private final TicketingRepository ticketingRepository;
    private final HeartRepository heartRepository;

    private final CrawRepository crawRepository;

    public ResponseDto<?> getMypage(HttpServletRequest request){
        //토큰이 있는지 확인
        if(jwtTokenProvider.validateToken(request)!=null){
            String[] responseMsg=jwtTokenProvider.validateToken(request).split(",");
            return ResponseDto.fail(responseMsg[0],responseMsg[1]);
        }

        //토큰에서 멤버 확인
        Member member=utilService.loggedInMember(request);
        if(member==null){
            return ResponseDto.fail("JWT claims is wrong", "잘못된 JWT 토큰 입니다");
        }

        List<Ticketing> temp_MyMoviesList = ticketingRepository.findByMember(member);
        ArrayList<MyMovieResponseDto> myMoviesList=new ArrayList<>();
        for (int i = 0; i < temp_MyMoviesList.size(); i++) {
            MyMovieResponseDto myMovieResponseDto = MyMovieResponseDto.builder()
                    .movieId(temp_MyMoviesList.get(i).getScreening().getMovie().getId())
                    .movieImg(temp_MyMoviesList.get(i).getScreening().getMovie().getImg())
                    .movieTitle(temp_MyMoviesList.get(i).getScreening().getMovie().getTitle())
                    .movieTitleEng(temp_MyMoviesList.get(i).getScreening().getMovie().getTitleEng())
                    .date(temp_MyMoviesList.get(i).getScreening().getDate())
                    .time(temp_MyMoviesList.get(i).getScreening().getTime())
                    .town(temp_MyMoviesList.get(i).getScreening().getCinema().getTown())
                    .memberCount(temp_MyMoviesList.get(i).getMemberCount()+"명")
                    .build();
            myMoviesList.add(myMovieResponseDto);
        }

        //본인이 찜하기한 영화 불러오기 위해 먼저 memberid로 해당 heart 불러오기
        List<CGVmovieHeart> movieHeartList = heartRepository.findByMemberId(member.getId());
        //불러온 heart에 해당하는 영화 넣어줄 리스트 생성
        List<Optional<CGVmovie>> movieList = new ArrayList<>();
        //클라이언트로 보내줄 영화를 담을 리스트 만들기
        List<MovieInfoResponseDto> mylikeMovies = new ArrayList<>();

        //mamberid로 불러온 heart로 해당 영화 불러오기
        for (int i = 0; i < movieHeartList.size(); i++) {
            movieList.add(crawRepository.findById(movieHeartList.get(i).getMovieId()));
        }

        //불러온 영화를 클라이언트에 보내줄 Dto 리스트에 원하는 정보 선별하여 담아주기
        for(Optional<CGVmovie> movie : movieList ){
            mylikeMovies.add(
                    MovieInfoResponseDto.builder()
                            .id(movie.get().getId())
                            .title(movie.get().getTitle())
                            .titleEng(movie.get().getTitleEng())
                            .img(movie.get().getImg())
                            .date(movie.get().getDate())
                            .director(movie.get().getDirector())
                            .actor(movie.get().getActor())
                            .rate(movie.get().getRate())
                            .genre(movie.get().getGenre())
                            .base(movie.get().getBase())
                            .detail(movie.get().getDetail())
                            .status(movie.get().getStatus())
                            .build()
            );
        }

        MyPageResponseDto myPageResponseDto = MyPageResponseDto.builder()
                .name(member.getName())
                .countMovie(temp_MyMoviesList.size())
                .countLike(heartRepository.findByMemberId(member.getId()).size())
                .likeMovies(mylikeMovies)
                .movies(myMoviesList)
                .build();

        return ResponseDto.success(myPageResponseDto);
    }
}
