package com.szuse.f4.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

@CrossOrigin
@RestController
public class OrderController {

  private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

  @Autowired
  private OrderMapper orderMapper;

  @Autowired
  private SeatMapper seatMapper;

  @Autowired
  private AreaMapper areaMapper;

  @GetMapping("/orders/history")
  public ResponseJSON getHistoryOrders(HttpServletRequest request) {

    User user = (User) request.getSession().getAttribute("user");
    Admin admin = (Admin) request.getSession().getAttribute("admin");
    JSONObject returnObject = new JSONObject();
    if (admin == null) {
      if (user == null) {
        throw new UnauthorizedException("Please login first");
      }
      // User
      returnObject.put("order_info", makeReturnObjects(orderMapper.getOrdersByUserId(user.getId())));
    } else {
      // Admin
      returnObject.put("order_info", makeReturnObjects(orderMapper.getOrders()));
    }
    return new ResponseJSON(200, "success", returnObject);
  }

  @GetMapping("/orders")
  public ResponseJSON getOrders(HttpServletRequest request,
      @RequestParam(value = "order_id", required = false, defaultValue = "0") int orderId) {

    User user = (User) request.getSession().getAttribute("user");
    Admin admin = (Admin) request.getSession().getAttribute("admin");
    JSONObject returnObject = new JSONObject();
    if (admin == null) {
      if (user == null) {
        throw new UnauthorizedException("Please login first");
      }
      if (orderId == 0) {
        Order[] myOrders = orderMapper.getValidOrdersByUserId(user.getId());
        returnObject.put("order_info", makeReturnObjects(myOrders));
      } else {
        Order[] orders = new Order[] { orderMapper.getOrderByOrderId(orderId) };
        if (orders[0].getUserId() != user.getId()) {
          throw new ForbiddenException("You are not authorized to view this order");
        }
        returnObject.put("order_info", makeReturnObjects(orders));
        return new ResponseJSON(200, "success", returnObject);
      }
    }
    // Admin
    if (orderId == 0) {
      // return all valid orders
      returnObject.put("order_info", makeReturnObjects(orderMapper.getValidOrders()));
    } else {
      Order[] orders = new Order[] { orderMapper.getOrderByOrderId(orderId) };
      returnObject.put("order_info", makeReturnObjects(orders));
    }
    return new ResponseJSON(200, "success", returnObject);
  }

  @PostMapping("/orders")
  public ResponseJSON insertOrder(HttpServletRequest request,
      @RequestBody Order order) {

    User user = (User) request.getSession().getAttribute("user");
    Admin admin = (Admin) request.getSession().getAttribute("admin");
    if (admin == null) {
      if (user == null) {
        throw new UnauthorizedException("Please login first");
      }
      order.setUserId(user.getId());
    }
    Seat seat = seatMapper.getSeatBySeatId(order.getSeatId());
    if (seat == null) {
      throw new ForbiddenException("Invalid seat id");
    }
    Timestamp appointmentTimestamp = order.getAppointmentTime();
    if (appointmentTimestamp.before(new Timestamp(System.currentTimeMillis()))) {
      throw new ForbiddenException("Invalid appointment time");
    }
    try {
      orderMapper.insertOrder(order);
      orderMapper.updateExpiredOrders();
    } catch (Exception e) {
      throw new ForbiddenException("Seat is not available");
    }
    return new ResponseJSON(200, "success");
  }

  @PutMapping("/orders")
  public ResponseJSON updateOrder(HttpServletRequest request,
      @RequestBody Order order) {

    User user = (User) request.getSession().getAttribute("user");
    Admin admin = (Admin) request.getSession().getAttribute("admin");
    if (admin == null) {
      if (user == null) {
        throw new UnauthorizedException("Please login first");
      } else if (order.getUserId() != user.getId()) {
        throw new ForbiddenException("You are not authorized to update this order");
      }
    }
    orderMapper.updateOrder(order);
    return new ResponseJSON(200, "success");
  }

  @DeleteMapping("/orders")
  public ResponseJSON deleteOrder(HttpServletRequest request,
      @RequestParam("order_id") int orderId) {

    Order order = orderMapper.getOrderByOrderId(orderId);
    User user = (User) request.getSession().getAttribute("user");
    Admin admin = (Admin) request.getSession().getAttribute("admin");
    if (admin == null) {
      if (user == null) {
        throw new UnauthorizedException("Please login first");
      } else if (order.getUserId() != user.getId()) {
        throw new ForbiddenException("You are not authorized to delete this order");
      }
    }
    orderMapper.deleteOrderByOrderId(orderId);
    return new ResponseJSON(200, "success");
  }

  private JSONObject makeReturnObject(Order order) {
    Seat seat = seatMapper.getSeatBySeatId(order.getSeatId());
    Area area = areaMapper.getAreaByAreaId(seat.getAreaId());
    JSONObject seatStatus = new JSONObject();
    seatStatus.put("seat_id", seat.getSeatId());
    seatStatus.put("seat_row", seat.getSeatRow());
    seatStatus.put("seat_col", seat.getSeatCol());
    JSONObject returnObject = new JSONObject();
    returnObject.put("order_id", order.getOrderId());
    returnObject.put("area_name", area.getAreaName());
    returnObject.put("time", sdf.format(order.getAppointmentTime()));
    returnObject.put("seat", seatStatus);
    returnObject.put("status", order.getOrderStatus());
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
