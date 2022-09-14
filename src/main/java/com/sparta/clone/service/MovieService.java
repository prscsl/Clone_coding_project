package com.sparta.clone.service;

import com.sparta.clone.controller.dto.request.MovieRequestDto;
import com.sparta.clone.controller.dto.response.MovieInfoResponseDto;
import com.sparta.clone.controller.dto.response.ResponseDto;
import com.sparta.clone.domain.CGVmovie;
import com.sparta.clone.domain.CGVmovieHeart;
import com.sparta.clone.domain.Member;
import com.sparta.clone.repository.CrawRepository;
import com.sparta.clone.repository.HeartRepository;
import com.sparta.clone.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MovieService {

    private final UtilService utilService;

    private final HeartRepository heartRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private final CrawRepository crawRepository;


    @Transactional
    public ResponseDto<?> getAllMovies(){
        // movie 전체 리스트 불러오기
        List<CGVmovie> movies = crawRepository.findAll();

        // movieInfoResponseDto.builder 내용 담을 리스트 만들기
        List<MovieInfoResponseDto> movieList = new ArrayList<>();

        //for문을 통해 movieList에 builder 형식으로 객체를 하나씩 넣어주기
        for(CGVmovie movie : movies){
            movieList.add(
                    MovieInfoResponseDto.builder()
                            .id(movie.getId())
                            .title(movie.getTitle())
                            .titleEng(movie.getTitleEng())
                            .img(movie.getImg())
                            .date(movie.getDate())
                            .director(movie.getDirector())
                            .actor(movie.getActor())
                            .rate(movie.getRate())
                            .genre(movie.getGenre())
                            .base(movie.getBase())
                            .detail(movie.getDetail())
                            .status(movie.getStatus())
                            .build()
            );
        }

        return ResponseDto.success(movieList);
    }



    @Transactional
    public ResponseDto<?> getDetailMovie( Long id) {

        //보내온 requestDto를 통해 해당 movie 불러오기
        CGVmovie movie = isPresentCGVmovie(id);
        if(null == movie){
            return ResponseDto.fail("NOT_FOUND","해당 영화가 존재하지 않습니다");
        }

        //for문을 통해 FR에서 원하는 형식에 맞게 보내주기
        return ResponseDto.success(
                MovieInfoResponseDto.builder()
                        .id(movie.getId())
                        .title(movie.getTitle())
                        .titleEng(movie.getTitleEng())
                        .img(movie.getImg())
                        .date(movie.getDate())
                        .director(movie.getDirector())
                        .actor(movie.getActor())
                        .rate(movie.getRate())
                        .genre(movie.getGenre())
                        .base(movie.getBase())
                        .detail(movie.getDetail())
                        .status(movie.getStatus())
                        .build()
        );


    }

    @Transactional
    public ResponseDto<?> likeMovie(Long id, HttpServletRequest request){

        //토큰이 있는지 확인
        if(null == request.getHeader("Authorization")){
            return ResponseDto.fail("LOGIN_IS_REQUIRED","로그인이 필요합니다.");
        }

        //받은 requestDto를 통해 해당 movie 불러오기
        CGVmovie movie = isPresentCGVmovie(id);
        if(null==movie){
            return ResponseDto.fail("NOT_FOUND","해당 영화가 존재하지 않습니다");
        }

        //토큰에서 멤버 정보 불러오기
        Member member = utilService.loggedInMember(request);
        if(null == member){
            return ResponseDto.fail("LOGIN_IS_REQUIRED","해당 유저가 없습니다");
        }

        //유저 정보 및 영화타이틀 정보 포함하여 하트 entity 불러오기
        CGVmovieHeart heart = isPresentHeart(movie.getId(), member.getId());

        // 불러온 하트변수값이 null이면 해당 영화에 로그인한 유저가 찜하기를 안한것으로 하트 정보 저장
        if(null == heart){
            heartRepository.save(CGVmovieHeart.builder()
                    .movieId(movie.getId()).memberId(member.getId())
                    .build());
        }else{
            heartRepository.delete(heart);
        }
        //하트변수가 null아닐시 예전에 찜하기를 한것으로, 찜하기 취소를 위해 저장된 하트 정보 삭제

        return ResponseDto.success("like success");

    }



    //받은 정보로 해당 movie 검증
    @Transactional(readOnly = true)
    public CGVmovie isPresentCGVmovie(Long id) {
        Optional<CGVmovie> optionalMovie = crawRepository.findById(id);
        return optionalMovie.orElse(null);
    }

    //받은 정보로 해당 heart 검증
    public  CGVmovieHeart isPresentHeart(Long movieid, String memberId) {
        Optional<CGVmovieHeart> optionalHeart = heartRepository.findByMovieIdAndMemberId(movieid,memberId);
        return optionalHeart.orElse(null);
    }


}
