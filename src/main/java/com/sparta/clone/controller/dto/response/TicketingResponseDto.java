package com.sparta.clone.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class TicketingResponseDto {
    private List<String> movies;
    private List<String> cities;
    private List<String> towns;
}
