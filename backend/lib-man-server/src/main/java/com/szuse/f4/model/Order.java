package com.szuse.f4.model;

import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Order {

  @JsonProperty("order_id")
  private int orderId;
  @JsonProperty("user_id")
  private int userId;
  @JsonProperty("seat_id")
  private int seatId;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
  @JsonProperty("order_time")
  private Timestamp orderTime;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
  @JsonProperty("appointment_time")
  private Timestamp appointmentTime;
  @JsonProperty("status")
  private int orderStatus;

  public Order() {
  }

  public Order(int orderId, int userId, int seatId, Timestamp orderTime, Timestamp appointmentTime, int orderStatus) {
    this.orderId = orderId;
    this.userId = userId;
    this.seatId = seatId;
    this.orderTime = orderTime;
    this.appointmentTime = appointmentTime;
    this.orderStatus = orderStatus;
  }

  public Order(int userId, int seatId, Timestamp appointmentTime) {
    this.userId = userId;
    this.seatId = seatId;
    this.appointmentTime = appointmentTime;
  }

  public int getOrderId() {
    return orderId;
  }

  public void setOrderId(int orderId) {
    this.orderId = orderId;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public int getSeatId() {
    return seatId;
  }

  public void setSeatId(int seatId) {
    this.seatId = seatId;
  }

  public Timestamp getOrderTime() {
    return orderTime;
  }

  public void setOrderTime(Timestamp orderTime) {
    this.orderTime = orderTime;
  }

  public Timestamp getAppointmentTime() {
    return appointmentTime;
  }

  public void setAppointmentTime(Timestamp appointmentTime) {
    this.appointmentTime = appointmentTime;
  }

  public int getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(int orderStatus) {
    this.orderStatus = orderStatus;
  }

}
