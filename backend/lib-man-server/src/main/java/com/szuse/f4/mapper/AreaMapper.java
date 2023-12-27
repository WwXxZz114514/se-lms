package com.szuse.f4.mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Result;
import com.szuse.f4.model.Area;

public interface AreaMapper {

    @Select("SELECT * FROM tb_area WHERE area_id = #{areaId}")
    @Results({
        @Result(property = "areaId", column = "area_id"),
        @Result(property = "areaName", column = "area_name")
    })
    Area getAreaByAreaId(int areaId);

    @Select("SELECT * FROM tb_area")
    @Results({
        @Result(property = "areaId", column = "area_id"),
        @Result(property = "areaName", column = "area_name")
    })
    Area[] getAreas();

    @Update("INSERT INTO tb_area (area_name) VALUES (#{areaName})")
    void insertArea(Area area);

    @Update("UPDATE tb_area SET area_name = #{areaName} WHERE area_id = #{areaId}")
    void updateArea(Area area);

    @Delete("DELETE FROM tb_area WHERE area_id = #{areaId}")
    void deleteAreaByAreaId(int areaId);

}
