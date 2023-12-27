package com.szuse.f4.controller;

import java.text.SimpleDateFormat;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import com.szuse.f4.model.*;
import com.szuse.f4.mapper.*;
import com.szuse.f4.common.exception.*;
import com.szuse.f4.common.ResponseJSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin
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

  @GetMapping("/seats")
  public ResponseJSON getSeatList(HttpServletRequest request,
      @RequestBody Query query) {

    int seatId = query.getSeatId();
    String appointmentTimeString = query.getAppointmentTimeString();
    int areaId = query.getAreaId();
    if (seatId != 0 && areaId != 0) {
      throw new BadRequestException("Invalid parameters");
    }

    JSONArray seatList = new JSONArray();
    if (areaId != 0) {
      Area area = areaMapper.getAreaByAreaId(areaId);
      Seat[] seats = seatMapper.getSeatsByAreaId(areaId);
      for (Seat seat : seats) {
        seatList.add(appointmentTimeString != null ? makeSeatListObject(seat, appointmentTimeString)
          : seat);
      }
    } else {
      Seat seat = seatMapper.getSeatBySeatId(seatId);
      seatList.add(appointmentTimeString != null ? makeSeatListObject(seat, appointmentTimeString)
        : seat);
    }

    JSONObject returnObject = new JSONObject();
    returnObject.put("seats_info", seatList);

    return new ResponseJSON(200, "success", returnObject);
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
    seatListObject.put("seat_id", seat.getSeatId());
    seatListObject.put("seat_row", seat.getSeatRow());
    seatListObject.put("seat_col", seat.getSeatCol());
    seatListObject.put("area_id", seat.getAreaId());
    seatListObject.put("type", order == null ? 0 : 1);
    return seatListObject;
  }

}

class Query {
  private int seatId;
  private String appointmentTimeString;
  private int areaId;

  public Query() {
  }

  public Query(int seatId, String appointmentTimeString, int areaId) {
    this.seatId = seatId;
    this.appointmentTimeString = appointmentTimeString;
    this.areaId = areaId;
  }

  public int getSeatId() {
    return seatId;
  }

  public void setSeatId(int seatId) {
    this.seatId = seatId;
  }

  public String getAppointmentTimeString() {
    return appointmentTimeString;
  }

  public void setAppointmentTimeString(String appointmentTimeString) {
    this.appointmentTimeString = appointmentTimeString;
  }

  public int getAreaId() {
    return areaId;
  }

  public void setAreaId(int areaId) {
    this.areaId = areaId;
  }

}