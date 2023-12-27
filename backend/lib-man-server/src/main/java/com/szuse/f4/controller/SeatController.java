package com.szuse.f4.controller;

import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import com.szuse.f4.model.Order;
import com.szuse.f4.model.Seat;
import com.szuse.f4.model.User;
import com.szuse.f4.model.Area;
import com.szuse.f4.mapper.SeatMapper;
import com.szuse.f4.mapper.OrderMapper;
import com.szuse.f4.mapper.AreaMapper;
import com.szuse.f4.common.exception.*;
import com.szuse.f4.common.ResponseJSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

  @Autowired
  private AreaMapper areaMapper;

  private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

  private static final JSONArray seatTypeList = new JSONArray() {
    {
      add(new JSONObject() {
        {
          put("type", 0);
          put("name", "Available");
        }
      });
      add(new JSONObject() {
        {
          put("type", 1);
          put("name", "Occupied");
        }
      });
      add(new JSONObject() {
        {
          put("type", 2);
          put("name", "Selected");
        }
      });
    }
  };

  @GetMapping("/seat")
  public ResponseJSON getSeatList(HttpServletRequest request,
      @RequestParam("areaId") int areaId,
      @RequestParam("appointmentTime") String appointmentTimeString) {

    Area area = areaMapper.getAreaByAreaId(areaId);
    Seat[] seats = seatMapper.getSeatsByAreaId(areaId);

    JSONArray seatList = new JSONArray();
    for (Seat seat : seats) {
      seatList.add(makeSeatListObject(seat, appointmentTimeString));
    }

    JSONObject areaStatus = new JSONObject();
    areaStatus.put("areaId", area.getAreaId());
    areaStatus.put("areaName", area.getAreaName());
    areaStatus.put("time", appointmentTimeString);
    areaStatus.put("seatList", seatList);
    areaStatus.put("seatTypeList", seatTypeList);

    return new ResponseJSON(0, "success", areaStatus);
  }

  private JSONObject makeSeatListObject(Seat seat, String appointmentTimeString) {
    Date appointmentTime;
    try {
      appointmentTime = sdf.parse(appointmentTimeString);
    } catch (ParseException e) {
      throw new BadRequestException("Invalid parameters");
    }
    Timestamp appointmentTimestamp = new Timestamp(appointmentTime.getTime());

    Order order = orderMapper.getOrderBySeatAndAppointmentTime(seat.getSeatId(), appointmentTimestamp);

    JSONObject seatListObject = new JSONObject();
    seatListObject.put("seatId", seat.getSeatId());
    seatListObject.put("row", seat.getSeatRow());
    seatListObject.put("col", seat.getSeatCol());
    seatListObject.put("type", order == null ? 0 : 1);
    return seatListObject;
  }

}
