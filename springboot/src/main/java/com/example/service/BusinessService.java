package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Business;
import com.example.exception.CustomException;
import com.example.mapper.BusinessMapper;
import org.springframework.stereotype.Service;
import java.lang.Object;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @Classname BusinessService
 * @Description TODO
 * @Author zjj
 * @Date 11/5/23 7:20PM
 */
@Service
public class BusinessService {
    @Resource
    private BusinessMapper businessMapper;

    /**
     * Add business service
     * @param business
     */
    public void add(Business business) {
        Business dbBusiness = selectByUsername(business.getUsername());
        //if current username already exist
        if (ObjectUtil.isNotEmpty(dbBusiness)){
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        business.setRole(RoleEnum.BUSINESS.name());
        businessMapper.insert(business);
    }

    /**
     * Get Business By username
     * return: at most one Business
     */
    public Business selectByUsername(String username){
        Business params = new Business();
        params.setUsername(username);
        List<Business> list = this.selectAll(params);
        return list.size() == 0 ? null : list.get(0);
    }

    /**
     * Get Business By id
     * return: at most one Business
     */
    public Business selectById(Integer id){
        Business params = new Business();
        params.setId(id);
        List<Business> list = this.selectAll(params);
        return list.size() == 0 ? null : list.get(0);
    }

    /**
     * Update business service
     * @param business
     */
    public void updateById(Business business) {
        //1. select by id to check if corresponding business exist
        Business dbBusiness1 = selectById(business.getId());
        if (ObjectUtil.isEmpty(dbBusiness1)){
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }

        //2. select by username to check after updating will result in duplicate username
        Business dbBusiness2 = selectByUsername(business.getUsername());
        if (ObjectUtil.isNotEmpty(dbBusiness2) && !Objects.equals(dbBusiness1.getId(), dbBusiness2.getId())){
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        businessMapper.updateById(business);
    }

    /**
     * Select All business service
     * @param business
     * @return
     */
    public List<Business> selectAll(Business business){
        return businessMapper.selectAll(business);
    }



}
