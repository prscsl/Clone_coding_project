package com.sparta.clone.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MyMovieResponseDto {
    private Long movieId;
    private String movieImg;
    private String movieTitle;
    private String movieTitleEng;
    private String date;
    private String time;
    private String town;
    private String memberCount;
}
