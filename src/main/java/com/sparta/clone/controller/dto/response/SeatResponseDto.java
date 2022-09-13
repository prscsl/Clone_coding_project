package com.sparta.clone.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class SeatResponseDto {
    private int maxSeat;
    private int remainingSeat;
    private String seatTypeString;
    private String[] seatTypeList;
}
