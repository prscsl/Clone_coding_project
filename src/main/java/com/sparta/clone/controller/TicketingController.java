package com.sparta.clone.controller;


import com.sparta.clone.controller.dto.request.TimeAndSeatDto;
import com.sparta.clone.controller.dto.response.ResponseDto;
import com.sparta.clone.service.ScreeningService;
import com.sparta.clone.service.TicketingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class TicketingController {
    private final ScreeningService screeningService;
    private final TicketingService ticketingService;

    @RequestMapping(value = "auth/ticket",method = RequestMethod.GET)
    public ResponseDto<?> movieAndCinema(HttpServletRequest request){
        return ticketingService.movieAndCinema(request);
    }

    @RequestMapping(value = "auth/ticket/select",method = RequestMethod.POST)
    public ResponseDto<?> timeAndSeat(@RequestBody TimeAndSeatDto timeAndSeatDto, HttpServletRequest request){
        return ticketingService.timeAndSeat(timeAndSeatDto,request);
    }

}
