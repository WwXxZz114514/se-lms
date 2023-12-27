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
        @Result(property = "appointmentTime", column = "appointment_time")
    })
    Order getOrderByOrderId(int orderId);

    @Select("SELECT * FROM tb_order")
    @Results({
        @Result(property = "orderId", column = "order_id"),
        @Result(property = "userId", column = "user_id"),
        @Result(property = "seatId", column = "seat_id"),
        @Result(property = "orderTime", column = "order_time"),
        @Result(property = "appointmentTime", column = "appointment_time")
    })
    Order[] getOrders();

    @Select("SELECT * FROM tb_order WHERE user_id = #{userId}")
    @Results({
        @Result(property = "orderId", column = "order_id"),
        @Result(property = "userId", column = "user_id"),
        @Result(property = "seatId", column = "seat_id"),
        @Result(property = "orderTime", column = "order_time"),
        @Result(property = "appointmentTime", column = "appointment_time")
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
        @Result(property = "appointmentTime", column = "appointment_time")
    })
    Order[] getOrdersByAppointmentTime(Timestamp appointmentTime);

    @Select("SELECT * FROM tb_order WHERE seat_id = #{seatId} AND appointment_time = #{appointmentTime}")
    @Results({
        @Result(property = "orderId", column = "order_id"),
        @Result(property = "userId", column = "user_id"),
        @Result(property = "seatId", column = "seat_id"),
        @Result(property = "orderTime", column = "order_time"),
        @Result(property = "appointmentTime", column = "appointment_time")
    })
    Order getOrderBySeatAndAppointmentTime(int seatId, Timestamp appointmentTime);

    @Delete("DELETE FROM tb_order WHERE order_id = #{orderId}")
    void deleteOrderByOrderId(int orderId);

}
