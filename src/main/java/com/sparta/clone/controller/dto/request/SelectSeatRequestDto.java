package com.sparta.clone.controller.dto.request;

import com.sparta.clone.domain.Screening;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SelectSeatRequestDto {
    private String title;

    private String town;
    private String date;

    private String time;

    private String seat;
    private int memberCount;
}
