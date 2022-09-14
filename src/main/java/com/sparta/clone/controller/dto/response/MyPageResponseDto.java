package com.sparta.clone.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;

@Getter
@AllArgsConstructor
@Builder
public class MyPageResponseDto {
    private String name;
    private int countMovie;
    private int countLike;
    private ArrayList<MyMovieResponseDto> movies;
}
