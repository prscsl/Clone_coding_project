package com.sparta.clone.controller;


import com.sparta.clone.controller.dto.request.MovieAndCinemaRequestDto;
import com.sparta.clone.controller.dto.request.SelectSeatRequestDto;
import com.sparta.clone.controller.dto.request.TimeAndSeatRequestDto;
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
    public ResponseDto<?> timeAndSeat(@RequestBody MovieAndCinemaRequestDto movieAndCinemaRequestDto, HttpServletRequest request){
        return ticketingService.timeAndSeat(movieAndCinemaRequestDto,request);
    }

    @RequestMapping(value = "auth/ticket/seat",method = RequestMethod.POST)
    public ResponseDto<?> seat(@RequestBody TimeAndSeatRequestDto timeAndSeatRequestDto, HttpServletRequest request){
        return ticketingService.seat(timeAndSeatRequestDto,request);
    }


    //Test


    @RequestMapping(value = "auth/ticket/seat/buy",method = RequestMethod.POST)
    public ResponseDto<?> buyTicket(@RequestBody SelectSeatRequestDto selectSeatRequestDto, HttpServletRequest request){
        return ticketingService.buyTicket(selectSeatRequestDto,request);
    }

}
