package com.sparta.clone.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;

@Getter
@AllArgsConstructor
@Builder
public class TicketResponseDto {
    private String[] movies;
    private String[] cinemas;
}
