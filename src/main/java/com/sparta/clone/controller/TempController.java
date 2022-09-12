package com.sparta.clone.controller;

import com.sparta.clone.controller.dto.response.*;
import com.sparta.clone.security.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
public class TempController {
    private final JwtTokenProvider tokenProvider;

    @RequestMapping(value = "/movie/{id}",method = RequestMethod.GET)
    public ResponseDto<?> movieInfo(@PathVariable String id){
        MovieInfoResponseDto moviesInfoResposeDto = MovieInfoResponseDto.builder()
                .title("공조2-인터내셔날")
                .titleEng("Confidential Assignment2 International")
                .img("https://img.cgv.co.kr/Movie/Thumbnail/Poster/000086/86155/86155_320.jpg")
                .date("2022.09.07")
                .director("이석훈")
                .actor("현빈, 유해진, 임윤아, 다니엘 헤니, 진선규")
                .rate("61.8")
                .genderRate("58.6")
                .genre("액션, 코미디")
                .base("15, 129분, 한국")
                .detail("공조 이즈 백! 이번엔 삼각 공조다!\n" +
                        "\n" +
                        "남한으로 숨어든 글로벌 범죄 조직을 잡기 위해\n" +
                        "새로운 공조 수사에 투입된 북한 형사 ‘림철령’(현빈).\n" +
                        "수사 중의 실수로 사이버수사대로 전출됐던 남한 형사 ‘강진태’(유해진)는\n" +
                        "광수대 복귀를 위해 모두가 기피하는 ‘철령’의 파트너를 자청한다.\n" +
                        "\n" +
                        "이렇게 다시 공조하게 된 ‘철령’과 ‘진태’!\n" +
                        "‘철령’과 재회한 ‘민영’(임윤아)의 마음도 불타오르는 가운데,\n" +
                        "‘철령’과 ‘진태’는 여전히 서로의 속내를 의심하면서도 나름 그럴싸한 공조 수사를 펼친다.\n" +
                        "드디어 범죄 조직 리더인 ‘장명준’(진선규)의 은신처를 찾아내려는 찰나,\n" +
                        "미국에서 날아온 FBI 소속 ‘잭’(다니엘 헤니)이 그들 앞에 나타나는데…!\n" +
                        "\n" +
                        "아직도 짠내 나는 남한 형사,\n" +
                        "여전한 엘리트 북한 형사,\n" +
                        "그리고 FBI 소속 해외파 형사까지!\n" +
                        "각자의 목적으로 뭉친 그들의 짜릿한 공조 수사가 시작된다!")
                .status(2)
                .build();
        return ResponseDto.success(moviesInfoResposeDto);
    }

    @RequestMapping(value = "/movies",method = RequestMethod.GET)
    public ResponseDto<?> movies(){
        // 영어이름 추가
        // status 추가
        ArrayList<String[]> movies = new ArrayList<>();
        String[] temp_movies={"공조2-인터내셔날","61.8%","https://img.cgv.co.kr/Movie/Thumbnail/Poster/000086/86155/86155_320.jpg","94","2022.09.07"};
        String[] temp_movies2={"육사오","8.5%","https://img.cgv.co.kr/Movie/Thumbnail/Poster/000086/86059/86059_320.jpg","94","2022.08.24"};
        movies.add(temp_movies);
        while (movies.size()!=19) {
            movies.add(temp_movies2);
            movies.add(temp_movies);
        }
        MoviesResponseDto moviesResponseDto = MoviesResponseDto.builder()
                .movies(movies)
                .build();
        return ResponseDto.success(moviesResponseDto);
        }

    @RequestMapping(value = "/auth/ticket",method = RequestMethod.GET)
    public ResponseDto<?> ticket(HttpServletRequest request){
            String token = request.getHeader("Authorization").substring(7);
            tokenProvider.validateToken(token);
       if(!tokenProvider.validateToken(token)){
           return ResponseDto.fail("삐빅-에러","뭔가 잘못됨");
       }


        String[] temp_movies={"공조2-인터네셔날","육사오","알라딘","탑건-메버릭"};
        String[] temp_cinemas={"강남","강변","구로","춘천"};
        TicketResponseDto ticketResponseDto = TicketResponseDto.builder()
                .movies(temp_movies)
                .cinemas(temp_cinemas)
                .build();
        return ResponseDto.success(ticketResponseDto);
    }

    }




