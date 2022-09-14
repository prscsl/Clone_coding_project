package com.sparta.clone.service;

import com.sparta.clone.controller.dto.response.MyMovieResponseDto;
import com.sparta.clone.controller.dto.response.MyPageResponseDto;
import com.sparta.clone.controller.dto.response.ResponseDto;
import com.sparta.clone.domain.Member;
import com.sparta.clone.domain.Ticketing;
import com.sparta.clone.repository.HeartRepository;
import com.sparta.clone.repository.TicketingRepository;
import com.sparta.clone.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UtilService utilService;
    private final JwtTokenProvider jwtTokenProvider;
    private final TicketingRepository ticketingRepository;
    private final HeartRepository heartRepository;

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
        MyPageResponseDto myPageResponseDto = MyPageResponseDto.builder()
                .name(member.getName())
                .countMovie(temp_MyMoviesList.size())
                .countLike(heartRepository.findByMemberId(member.getId()).size())
                .movies(myMoviesList)
                .build();

        return ResponseDto.success(myPageResponseDto);
    }
}
