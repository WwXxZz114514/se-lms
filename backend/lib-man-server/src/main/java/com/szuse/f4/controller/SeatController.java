package com.szuse.f4.controller;

import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import com.szuse.f4.model.*;
import com.szuse.f4.mapper.*;
import com.szuse.f4.common.exception.*;
import com.szuse.f4.common.ResponseJSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class SeatController {

  @Autowired
  private SeatMapper seatMapper;

  @Autowired
  private OrderMapper orderMapper;

  private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

  @GetMapping("/seats")
  public ResponseJSON getSeatList(HttpServletRequest request,
      @RequestParam(value = "seat_id", required = false, defaultValue = "0") int seatId,
      @RequestParam(value = "appointment_time", required = false, defaultValue = "") String appointmentTimeString,
      @RequestParam(value = "area_id", required = false, defaultValue = "0") int areaId) {

    if (seatId != 0 && areaId != 0) {
      throw new BadRequestException("Invalid parameters");
    }

    Date appointmentTime = null;
    if (!appointmentTimeString.equals("")) {
      try {
        appointmentTime = sdf.parse(appointmentTimeString);
      } catch (ParseException e) {
        throw new BadRequestException("Invalid time format");
      }
    }

    JSONArray seatList = new JSONArray();
    if (areaId != 0) {
      Seat[] seats = seatMapper.getSeatsByAreaId(areaId);
      for (Seat seat : seats) {
        seatList.add(appointmentTime != null ? makeSeatListObject(seat, appointmentTime) : seat);
      }
    } else if (seatId != 0) {
      Seat seat = seatMapper.getSeatBySeatId(seatId);
      seatList.add(appointmentTime != null ? makeSeatListObject(seat, appointmentTime) : seat);
    } else {
      throw new BadRequestException("Area id or seat id must be specified");
    }

    JSONObject returnObject = new JSONObject();
    returnObject.put("seats_info", seatList);

    return new ResponseJSON(200, "success", returnObject);
  }

  private JSONObject makeSeatListObject(Seat seat, Date appointmentTime) {
    Timestamp appointmentTimestamp = new Timestamp(appointmentTime.getTime());

    Order order = orderMapper.getOrderBySeatAndAppointmentTime(seat.getSeatId(), appointmentTimestamp);
    boolean isAvailable = false;
    if (order == null || order.getOrderStatus() != 0) {
      isAvailable = true;
    }

    JSONObject seatListObject = new JSONObject();
    seatListObject.put("seat_id", seat.getSeatId());
    seatListObject.put("seat_row", seat.getSeatRow());
    seatListObject.put("seat_col", seat.getSeatCol());
    seatListObject.put("area_id", seat.getAreaId());
    seatListObject.put("type", isAvailable ? 1 : 0);
    return seatListObject;
  }

}