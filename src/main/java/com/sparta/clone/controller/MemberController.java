package com.sparta.clone.controller;

import com.nimbusds.jose.shaded.json.parser.ParseException;
import com.sparta.clone.controller.dto.response.ResponseDto;
import com.sparta.clone.service.MyPageService;
import com.sparta.clone.service.NaverLoginApi;
import com.sparta.clone.service.UtilService;
import com.sparta.clone.service.KakaoLoginApi;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
//@Controller : 주로 View를 반환
//@RestController : 주로 Data를 반환 (@Controller+ Method에 @ResponseBody 추가)
//Generic 명시
@RequiredArgsConstructor
public class MemberController {

    private final NaverLoginApi naverLoginApi;

    private final KakaoLoginApi kakaoLoginApi;

    private final UtilService utilService;
    private final MyPageService myPageService;


//    // 네이버에서 제공하는 로그인창으로 이동
//    @RequestMapping(value = "/auth/naver")
//    public String naverLogin(){
//        return "redirect:"+naverLoginApi.makeLoginUrl();
//        //성공시 developer 지정한 call-back URL로 redirect
//    }

    // call-back URL -> 정보조회 (-> 회원가입) -> 로그인
    @RequestMapping(value = "/auth/naver")
    // ResponseDto 앞에 ResponseBody 추가시 RestController로 시행
    public @ResponseBody ResponseDto<?> naverAuth(@RequestParam String code, @RequestParam String state, HttpServletResponse response){
        // call-back URL 로 올시 code와 state를 파라미터로 반영해서 옴
        return naverLoginApi.login(code,state,response);
        // 받은 code와 state를 기반으로 Token발급 및 정보 조회
    }

    @RequestMapping(value = "/auth/kakao")
    // ResponseDto 앞에 ResponseBody 추가시 RestController로 시행
    public @ResponseBody ResponseDto<String> kakaoAuth(@RequestParam String code, HttpServletResponse httpServletResponse) throws IOException, ParseException {
        // call-back URL 로 올시 code 파라미터로 반영해서 옴
        return kakaoLoginApi.getKakaoInfo(code, httpServletResponse);
        // 받은 code와 기반으로 Token발급 및 정보 조회
    }

    //Test 토큰 정보 조회
    @GetMapping(value = "/test/auth/info")
    public @ResponseBody UserDetails testInfoByToken(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;

        return userDetails;
    }

    //Test 로그인한 유저 정보 조회
    @GetMapping(value = "/test/auth/member")
    public @ResponseBody ResponseDto<?> MemberInfo(HttpServletRequest request){
        return ResponseDto.success(utilService.loggedInMember(request));
    }


    //My Page
    @GetMapping(value = "/user/movielog")
    public @ResponseBody ResponseDto<?> MyPage(HttpServletRequest request){
        return myPageService.getMypage(request);
    }



}
