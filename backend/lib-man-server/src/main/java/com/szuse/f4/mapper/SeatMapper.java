package com.szuse.f4.mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import com.szuse.f4.model.Seat;

public interface SeatMapper {

    @Select("SELECT * FROM tb_seat WHERE seat_id = #{seatId}")
    @Results({
        @Result(property = "seatId", column = "seat_id"),
        @Result(property = "seatRow", column = "seat_row"),
        @Result(property = "seatCol", column = "seat_col"),
        @Result(property = "areaId", column = "area_id")
    })
    Seat getSeatBySeatId(int seatId);

    @Update("INSERT INTO tb_seat (seat_row, seat_col, area_id) VALUES (#{seatRow}, #{seatCol}, #{areaId})")
    void insertSeat(Seat seat);

    @Select("SELECT * FROM tb_seat WHERE area_id = #{areaId}")
    @Results({
        @Result(property = "seatId", column = "seat_id"),
        @Result(property = "seatRow", column = "seat_row"),
        @Result(property = "seatCol", column = "seat_col"),
        @Result(property = "areaId", column = "area_id")
    })
    Seat[] getSeatsByAreaId(int areaId);

}
