package com.example.service;

import cn.hutool.core.collection.CollUtil;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Business;
import com.example.exception.CustomException;
import com.example.mapper.BusinessMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    public List<Business> selectAll(Business business){
        return businessMapper.selectAll(business);
    }

    public void add(Business business) {
        Business params = new Business();
        params.setUsername(business.getUsername());
        List<Business> list = this.selectAll(params);
        //if current username already exist
        if (CollUtil.isNotEmpty(list)){
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        business.setRole(RoleEnum.BUSINESS.name());
        businessMapper.insert(business);
    }
}
