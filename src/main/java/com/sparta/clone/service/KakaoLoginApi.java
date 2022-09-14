package com.sparta.clone.service;


import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jose.shaded.json.parser.JSONParser;
import com.nimbusds.jose.shaded.json.parser.ParseException;
import com.sparta.clone.controller.dto.response.ResponseDto;
import com.sparta.clone.domain.Member;
import com.sparta.clone.repository.MemberRepository;
import com.sparta.clone.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KakaoLoginApi extends DefaultOAuth2UserService {

    private final MemberRepository memberRepo;

    private final JwtTokenProvider jwtTokenProvider;

    public ResponseDto<String> getKakaoInfo(String code, HttpServletResponse httpServletResponse) throws IOException, ParseException {
        //요청 보낼 url
        URL url = new URL("https://kauth.kakao.com/oauth/token");
        // url 연결
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        // 요청 보낼 형식
        httpConn.setRequestMethod("POST");
        // 요청 파일 타입
        httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        //https://kauth.kakao.com/oauth/authorize?client_id=32936f1096342364d3854a2f3015a410&redirect_uri=http://3.36.70.96:8080/auth/kakao
        //https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=32936f1096342364d3854a2f3015a410&redirect_uri=http://3.36.70.96:8080/auth/kakao

        httpConn.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());

        //카카오 Client id와 프론트에서 받은 code 입력
        writer.write("grant_type=authorization_code&client_id=32936f1096342364d3854a2f3015a410&code="+code);
        writer.flush();
        writer.close();
        httpConn.getOutputStream().close();

        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream();
        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        String response = s.hasNext() ? s.next() : "";

        //response json 형태변환
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(response);
        JSONObject jsonObj = (JSONObject) obj;
        System.out.println(jsonObj.get("access_token"));
        String token2 = (String) jsonObj.get("access_token");

        //요청 보낼 주소 입력
        URL url2 = new URL("https://kapi.kakao.com/v2/user/me");
        HttpURLConnection httpConn2 = (HttpURLConnection) url2.openConnection();
        // 요청 방식 입력
        httpConn2.setRequestMethod("GET");
        // 요청 데이터 입력 token2 = json으로 형태변환한 response
        httpConn2.setRequestProperty("Authorization", "Bearer "+token2);

        InputStream responseStream2 = httpConn2.getResponseCode() / 100 == 2
                ? httpConn2.getInputStream()
                : httpConn2.getErrorStream();
        Scanner sc = new Scanner(responseStream2).useDelimiter("\\A");
        String response2 = sc.hasNext() ? sc.next() : "";
        System.out.println(response2);

        //response2 json로 변환
        Object obj2 = jsonParser.parse(response2);
        JSONObject jsonObj2 = (JSONObject) obj2;

        //회원이 가입되어있는지 확인 메서드 실행
        signIn(jsonObj2);

        //id값 뺴서 해당 member 불러오기
        String id = String.valueOf(jsonObj2.get("id"));
        Member member = memberRepo.findById(id).get();

        // 토큰생성
        String accessToken = jwtTokenProvider.creatToken(member);


        // 생성된 토큰 해더에 추가
        httpServletResponse.addHeader("Authorization","Bearer "+accessToken);
        System.out.println("accesstoken : "+accessToken);

        String msg = member.getName()+"님 반갑습니다.";
        return ResponseDto.success(msg);
    }

    public void signIn(JSONObject userData){
        //id,name,gender,birthyear userData에서 추출하여 입력
        String id = String.valueOf(userData.get("id"));
        String name = String.valueOf(userData.get("nickname"));
        int gender=0;
        if (String.valueOf(userData.get("gender")).equals("male")){
            gender=1;
        }
        if (String.valueOf(userData.get("gender")).equals("bmale")){
            gender=2;
        }
        int birthyear= Integer.valueOf(String.valueOf(userData.get("0000.00.00")));

        //해당 id member 없을경우 member 생성, 아니면 바로 로그인
        if(!memberRepo.existsById(id)){
            Member member = Member.builder()
                    .id(id)
                    .name(name)
                    .gender(gender)
                    .birthyear(birthyear)
                    .userRole("ROLE_USER")
                    .build();
            memberRepo.save(member);
        }
        String msg = name+"님 환영합니다.";
    }

}

