package com.sparta.clone.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MovieInfoResponseDto {

    private String title;
    private String titleEng;
    private String img;
    private String date;
    private String director;
    private String actor;
    private String rate;
    private String genderRate;
    private String genre;
    private String base;
    private String detail;
    private int status;
}
