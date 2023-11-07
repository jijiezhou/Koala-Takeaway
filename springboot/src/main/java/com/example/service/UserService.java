package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.User;
import com.example.exception.CustomException;
import com.example.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @Classname UserService
 * @Description TODO
 * @Author zjj
 * @Date 11/7/23 4:38PM
 */

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    /**
     * Add user service
     *
     * @param user
     */
    public void add(User user) {
        User dbuser = selectByUsername(user.getUsername());
        //if current username already exist
        if (ObjectUtil.isNotEmpty(dbuser)) {
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        if (ObjectUtil.isEmpty(user.getName())) {
            user.setName(user.getUsername());
        }
        user.setRole(RoleEnum.USER.name());
        userMapper.insert(user);
    }

    /**
     * Delete By Id
     *
     * @param id
     */
    public void deleteById(Integer id) {
        userMapper.deleteById(id);
    }

    /**
     * Delete Batch
     *
     * @param ids
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            deleteById(id);
        }
    }

    /**
     * Update user service
     *
     * @param user
     */
    public void updateById(User user) {
        //1. select by id to check if corresponding user exist
        User dbuser1 = selectById(user.getId());
        if (ObjectUtil.isEmpty(dbuser1)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }

        //2. select by username to check after updating will result in duplicate username
        User dbuser2 = selectByUsername(user.getUsername());
        if (ObjectUtil.isNotEmpty(dbuser2) && !Objects.equals(dbuser1.getId(), dbuser2.getId())) {
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        userMapper.updateById(user);
    }

    /**
     * Select All user service
     *
     * @param user
     * @return
     */
    public List<User> selectAll(User user) {
        return userMapper.selectAll(user);
    }

    /**
     * Select user By username
     * return: at most one user
     */
    public User selectByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    /**
     * Select user By id
     * return: at most one user
     */
    public User selectById(Integer id) {
        return userMapper.selectById(id);
    }

    /**
     * SelectPage
     *
     * @param user
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<User> selectPage(User user, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = userMapper.selectAll(user);
        return PageInfo.of(list);
    }
}

