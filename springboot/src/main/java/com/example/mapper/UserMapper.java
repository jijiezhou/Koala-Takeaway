package com.example.mapper;

import com.example.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Classname UserMapper
 * @Description TODO
 * @Author zjj
 * @Date 11/7/23 4:33PM
 */

public interface UserMapper {
    /**
     * Insert User
     * @param user
     * @return
     */
    int insert(User user);

    /**
     * Delete User
     * @param id
     * @return
     */
    int deleteById(Integer id);

    /**
     * Update User
     * @param user
     * @return
     */
    int updateById(User user);

    /**
     * Select By Id
     * @param id
     * @return
     */
    User selectById(Integer id);

    /**
     * SelectAll
     * @param user
     * @return
     */
    List<User> selectAll(User user);

    @Select("select * from user where username = #{username}")
    User selectByUsername(String username);
}
