package com.sparta.clone.controller.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MovieAndCinemaRequestDto {
    private String title;
    private String town;
    private String date;
}
