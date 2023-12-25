package com.szuse.f4.mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import com.szuse.f4.model.User;

public interface UserMapper {

    @Select("SELECT * FROM tb_user WHERE username = #{username}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "tel", column = "tel"),
            @Result(property = "password", column = "password"),
            @Result(property = "createTime", column = "create_time")
    })
    User getUserByUsername(String username);

    @Update("INSERT INTO tb_user (username, tel, password) VALUES (#{username}, #{tel}, #{password})")
    void insertUser(User user);

    @Update("UPDATE tb_user SET password = #{password} WHERE username = #{username}")
    void updatePassword(User user);

}
