package com.sparta.clone.controller;

import com.sparta.clone.controller.dto.request.MovieRequestDto;
import com.sparta.clone.controller.dto.response.ResponseDto;
import com.sparta.clone.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class MovieController {

    private final MovieService movieService;

    @RequestMapping(value = "/movies", method = RequestMethod.GET)
    public ResponseDto<?> getAllMovies(){return movieService.getAllMovies();}

    @RequestMapping(value = "/movie",method = RequestMethod.GET)
    public ResponseDto<?> getDetailMovie(@RequestBody MovieRequestDto movieRequestDto){
        return movieService.getDetailMovie(movieRequestDto);
    }

    @RequestMapping(value = "/movie/like", method = RequestMethod.POST)
    public ResponseDto<?> likeMovie(@RequestBody MovieRequestDto movieRequestDto, HttpServletRequest request){
        return movieService.likeMovie(movieRequestDto,request);
    }


}
