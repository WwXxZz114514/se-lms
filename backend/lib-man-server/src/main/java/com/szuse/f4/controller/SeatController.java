package com.szuse.f4.controller;

import com.szuse.f4.model.Seat;
import com.szuse.f4.mapper.SeatMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class SeatController {

  @Autowired
  private SeatMapper seatMapper;

  @PostMapping("/seat/order")
  public String login(HttpServletRequest request,
                      @RequestParam("username") String username,
                      @RequestParam("tel") String tel,
                      @RequestParam("password") String password) {
    seatMapper.insertSeat(new Seat());
    return "";
  }

}
