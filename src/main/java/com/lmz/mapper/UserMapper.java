package com.lmz.mapper;

import com.lmz.vo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    @Select("select * from user where username=#{map.username} and password=#{map.password}")
    User login(@Param("map") Map<String,String> map);

    @Insert("insert into user (username,password,permission,stat) values(#{map.username},#{map.password},0,1)")
    int register(@Param("map") Map<String, String> map);

    @Select("select * from user")
    List<User> userlist();

    @Update("update user set stat=#{map.stat} where username=#{map.username}")
    void chmod(@Param("map") Map<String, String> map);
}
