package com.szuse.f4.controller;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import com.alibaba.fastjson2.JSONObject;

import com.szuse.f4.model.*;
import com.szuse.f4.mapper.*;
import com.szuse.f4.common.ResponseJSON;
import com.szuse.f4.common.exception.*;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class OrderController {

  private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

  @Autowired
  private OrderMapper orderMapper;

  @Autowired
  private SeatMapper seatMapper;

  @Autowired
  private AreaMapper areaMapper;

  @GetMapping("/order/{orderId}")
  public ResponseJSON getOrder(HttpServletRequest request,
      @PathVariable("orderId") int orderId) {

    User user = (User) request.getSession().getAttribute("user");
    Admin admin = (Admin) request.getSession().getAttribute("admin");
    Order order = orderMapper.getOrderByOrderId(orderId);
    if (admin == null) {
      if (user == null) {
        throw new UnauthorizedException("Please login first");
      } else if (order.getUserId() != user.getId()) {
        throw new UnauthorizedException("You are not authorized to get this order");
      }
    }
    return new ResponseJSON(0, "success", makeReturnObject(order));
  }

  @GetMapping("/order")
  public ResponseJSON getOrders(HttpServletRequest request) {

    User user = (User) request.getSession().getAttribute("user");
    Admin admin = (Admin) request.getSession().getAttribute("admin");
    if (admin == null) {
      if (user == null) {
        throw new UnauthorizedException("Please login first");
      }
      return new ResponseJSON(0, "success", orderMapper.getOrdersByUserId(user.getId()));
    }
    return new ResponseJSON(0, "success", makeReturnObjects(orderMapper.getOrders()));
  }

  @PostMapping("/order")
  public ResponseJSON insertOrder(HttpServletRequest request,
      @RequestBody Order order) {

    User user = (User) request.getSession().getAttribute("user");
    Admin admin = (Admin) request.getSession().getAttribute("admin");
    if (admin == null) {
      if (user == null) {
        throw new UnauthorizedException("Please login first");
      } else if (order.getUserId() != user.getId()) {
        throw new UnauthorizedException("You are not authorized to update this order");
      }
    }
    orderMapper.insertOrder(order);
    return new ResponseJSON(0, "success");
  }

  @PutMapping("/order")
  public ResponseJSON updateOrder(HttpServletRequest request,
      @RequestBody Order order) {

    User user = (User) request.getSession().getAttribute("user");
    Admin admin = (Admin) request.getSession().getAttribute("admin");
    if (admin == null) {
      if (user == null) {
        throw new UnauthorizedException("Please login first");
      } else if (order.getUserId() != user.getId()) {
        throw new UnauthorizedException("You are not authorized to update this order");
      }
    }
    orderMapper.updateOrder(order);
    return new ResponseJSON(0, "success");
  }

  @DeleteMapping("/order")
  public ResponseJSON deleteOrder(HttpServletRequest request,
      @RequestParam("orderId") int orderId) {

    Order order = orderMapper.getOrderByOrderId(orderId);
    User user = (User) request.getSession().getAttribute("user");
    Admin admin = (Admin) request.getSession().getAttribute("admin");
    if (admin == null) {
      if (user == null) {
        throw new UnauthorizedException("Please login first");
      } else if (order.getUserId() != user.getId()) {
        throw new UnauthorizedException("You are not authorized to delete this order");
      }
    }
    orderMapper.deleteOrderByOrderId(orderId);
    return new ResponseJSON(0, "success");
  }

  private JSONObject makeReturnObject(Order order) {
    Seat seat = seatMapper.getSeatBySeatId(order.getSeatId());
    Area area = areaMapper.getAreaByAreaId(seat.getAreaId());
    JSONObject seatStatus = new JSONObject();
    seatStatus.put("seatId", seat.getSeatId());
    seatStatus.put("row", seat.getSeatRow());
    seatStatus.put("col", seat.getSeatCol());
    seatStatus.put("type", 1);
    JSONObject returnObject = new JSONObject();
    returnObject.put("orderId", order.getOrderId());
    returnObject.put("areaName", area.getAreaName());
    returnObject.put("time", sdf.format(order.getAppointmentTime()));
    returnObject.put("seat", seatStatus);
    return returnObject;
  }

  private JSONObject[] makeReturnObjects(Order[] orders) {
    JSONObject[] returnObjects = new JSONObject[orders.length];
    for (int i = 0; i < orders.length; i++) {
      returnObjects[i] = makeReturnObject(orders[i]);
    }
    return returnObjects;
  }

}
