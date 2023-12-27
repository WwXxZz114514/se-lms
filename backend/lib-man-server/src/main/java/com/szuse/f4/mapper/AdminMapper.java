package com.szuse.f4.mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import com.szuse.f4.model.Admin;

public interface AdminMapper {

  @Select("SELECT * FROM tb_admin WHERE username = #{username}")
  @Results({
      @Result(property = "id", column = "id"),
      @Result(property = "username", column = "username"),
      @Result(property = "tel", column = "tel"),
      @Result(property = "password", column = "password"),
      @Result(property = "createTime", column = "create_time")
  })
  Admin getAdminByUsername(String username);

  @Update("INSERT INTO tb_admin (username, tel, password) VALUES (#{username}, #{tel}, #{password})")
  void insertAdmin(Admin admin);

  @Update("UPDATE tb_admin SET password = #{password} WHERE username = #{username}")
  void updatePassword(Admin admin);

}
