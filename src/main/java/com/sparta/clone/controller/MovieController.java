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

    @RequestMapping(value = "/movie/{id}",method = RequestMethod.GET)
    public ResponseDto<?> getDetailMovie(@PathVariable Long id){
        return movieService.getDetailMovie(id);
    }

    @RequestMapping(value = "/movie/like", method = RequestMethod.POST)
    public ResponseDto<?> likeMovie(@PathVariable Long id, HttpServletRequest request){
        return movieService.likeMovie(id,request);
    }


}
