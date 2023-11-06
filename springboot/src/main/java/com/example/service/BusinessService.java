package com.example.service;

import com.example.entity.Business;
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

    public List<Business> selectAll(){
        return businessMapper.selectAll();
    }

    public void add(Business business) {
        businessMapper.insert(business);
    }
}
