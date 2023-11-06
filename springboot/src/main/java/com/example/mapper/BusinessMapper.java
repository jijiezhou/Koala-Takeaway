package com.example.mapper;

import com.example.entity.Business;

import java.util.List;

/**
 * @Classname BusinessMapper
 * @Description TODO
 * @Author zjj
 * @Date 11/5/23 7:38PM
 */
public interface BusinessMapper {
    List<Business> selectAll(Business business);

    int insert(Business business);

    int updateById(Business business);
}
