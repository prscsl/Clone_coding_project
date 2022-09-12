package com.sparta.clone.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MyPageResponseDto {
    private String name;
    private int countMovie;
    private int countLike;
    private String[] movies;
}
