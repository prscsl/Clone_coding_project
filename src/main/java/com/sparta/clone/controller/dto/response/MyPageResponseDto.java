package com.sparta.clone.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class MyPageResponseDto {
    private String name;
    private int countMovie;
    private int countLike;
    private List<MovieInfoResponseDto> likeMovies;
    private ArrayList<MyMovieResponseDto> movies;
}
