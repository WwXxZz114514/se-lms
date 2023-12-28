package com.szuse.f4.mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Results;

import java.sql.Timestamp;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Result;
import com.szuse.f4.model.Order;

public interface OrderMapper {

  @Select("SELECT * FROM tb_order WHERE order_id = #{orderId}")
  @Results({
      @Result(property = "orderId", column = "order_id"),
      @Result(property = "userId", column = "user_id"),
      @Result(property = "seatId", column = "seat_id"),
      @Result(property = "orderTime", column = "order_time"),
      @Result(property = "appointmentTime", column = "appointment_time"),
      @Result(property = "orderStatus", column = "order_status")
  })
  Order getOrderByOrderId(int orderId);

  @Select("SELECT * FROM tb_order")
  @Results({
      @Result(property = "orderId", column = "order_id"),
      @Result(property = "userId", column = "user_id"),
      @Result(property = "seatId", column = "seat_id"),
      @Result(property = "orderTime", column = "order_time"),
      @Result(property = "appointmentTime", column = "appointment_time"),
      @Result(property = "orderStatus", column = "order_status")
  })
  Order[] getOrders();

  @Select("SELECT * FROM tb_order WHERE user_id = #{userId}")
  @Results({
      @Result(property = "orderId", column = "order_id"),
      @Result(property = "userId", column = "user_id"),
      @Result(property = "seatId", column = "seat_id"),
      @Result(property = "orderTime", column = "order_time"),
      @Result(property = "appointmentTime", column = "appointment_time"),
      @Result(property = "orderStatus", column = "order_status")
  })
  Order[] getOrdersByUserId(int userId);

  @Update("INSERT INTO tb_order (user_id, seat_id, appointment_time) VALUES (#{userId}, #{seatId}, #{appointmentTime})")
  void insertOrder(Order order);

  @Update("UPDATE tb_order SET user_id = #{userId}, seat_id = #{seatId}, appointment_time = #{appointmentTime} WHERE order_id = #{orderId}")
  void updateOrder(Order order);

  @Select("SELECT * FROM tb_order WHERE appointment_time = #{appointmentTime}")
  @Results({
      @Result(property = "orderId", column = "order_id"),
      @Result(property = "userId", column = "user_id"),
      @Result(property = "seatId", column = "seat_id"),
      @Result(property = "orderTime", column = "order_time"),
      @Result(property = "appointmentTime", column = "appointment_time"),
      @Result(property = "orderStatus", column = "order_status")
  })
  Order[] getOrdersByAppointmentTime(Timestamp appointmentTime);

  @Select("SELECT * FROM tb_order WHERE appointment_time < NOW() AND seat_id = #{seatId}")
  @Results({
      @Result(property = "orderId", column = "order_id"),
      @Result(property = "userId", column = "user_id"),
      @Result(property = "seatId", column = "seat_id"),
      @Result(property = "orderTime", column = "order_time"),
      @Result(property = "appointmentTime", column = "appointment_time"),
      @Result(property = "orderStatus", column = "order_status")
  })
  Order getOrderBySeatAndAppointmentTime(int seatId, Timestamp appointmentTime);

  @Select("SELECT * FROM tb_order WHERE order_status = 0")
  @Results({
      @Result(property = "orderId", column = "order_id"),
      @Result(property = "userId", column = "user_id"),
      @Result(property = "seatId", column = "seat_id"),
      @Result(property = "orderTime", column = "order_time"),
      @Result(property = "appointmentTime", column = "appointment_time"),
      @Result(property = "orderStatus", column = "order_status")
  })
  Order[] getValidOrders();

  @Select("SELECT * FROM tb_order WHERE order_status = 0 AND user_id = #{userId}")
  @Results({
      @Result(property = "orderId", column = "order_id"),
      @Result(property = "userId", column = "user_id"),
      @Result(property = "seatId", column = "seat_id"),
      @Result(property = "orderTime", column = "order_time"),
      @Result(property = "appointmentTime", column = "appointment_time"),
      @Result(property = "orderStatus", column = "order_status")
  })
  Order[] getValidOrdersByUserId(int userId);

  @Delete("DELETE FROM tb_order WHERE order_id = #{orderId}")
  void deleteOrderByOrderId(int orderId);

  @Update("UPDATE tb_order SET order_status = 2 WHERE appointment_time < NOW() AND order_status = 0")
  void updateExpiredOrders();

}
