package com.sparta.clone.service;

import com.sparta.clone.controller.dto.response.MovieInfoResponseDto;
import com.sparta.clone.controller.dto.response.ResponseDto;
import com.sparta.clone.domain.CGVmovie;
import com.sparta.clone.domain.CGVmovieHeart;
import com.sparta.clone.domain.Member;
import com.sparta.clone.repository.CrawRepository;
import com.sparta.clone.repository.HeartRepository;
import com.sparta.clone.security.jwt.JwtCustomFilter;
import com.sparta.clone.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MovieService {

    private final UtilService utilService;

    private final HeartRepository heartRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private final CrawRepository crawRepository;

    @Transactional
    public ResponseDto<?> getDetailMovie(Long id) {
        CGVmovie movie = isPresentCGVmovie(id);
        if(null == movie){
            return ResponseDto.fail("NOT_FOUND","해당 영화가 존재하지 않습니다");
        }

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
    public ResponseDto<?> likeMovie(@PathVariable Long id, HttpServletRequest request){

        if(null == request.getHeader("Authorization")){
            return ResponseDto.fail("LOGIN_IS_REQUIRED","로그인이 필요합니다.");
        }

        CGVmovie movie = isPresentCGVmovie(id);
        if(null==movie){
            return ResponseDto.fail("NOT_FOUND","해당 영화가 존재하지 않습니다");
        }

        Member member = utilService.loggedInMember(request);
        if(null == member){
            return ResponseDto.fail("LOGIN_IS_REQUIRED","해당 유저가 없습니다");
        }

        CGVmovieHeart heart = isPresentHeart(movie.getId(), member.getId());

        if(null == heart){
            heartRepository.save(CGVmovieHeart.builder().requestId(movie.getId()).name(member.getId()).build());
        }else{
            heartRepository.delete(heart);
        }

        return ResponseDto.success("like success");

    }



    @Transactional(readOnly = true)
    public CGVmovie isPresentCGVmovie(Long id) {
        Optional<CGVmovie> optionalMovie = crawRepository.findById(id);
        return optionalMovie.orElse(null);
    }

    public  CGVmovieHeart isPresentHeart(Long movieId, String memberId) {
        Optional<CGVmovieHeart> optionalHeart = heartRepository.findByRequestIdAndMemberId(movieId,memberId);
        return optionalHeart.orElse(null);
    }


}
