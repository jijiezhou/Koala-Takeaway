package com.example.mapper;
import com.example.entity.Banner;

import java.util.List;

/**
 * @Classname BannerMapper
 * @Description TODO
 * @Author zjj
 * @Date 11/11/23 4:52PM
 */
public interface BannerMapper {
    /**
     * Insert
     */
    int insert(Banner banner);

    /**
     * DeleteById
     */
    int deleteById(Integer id);

    /**
     * Update
     */
    int updateById(Banner banner);

    /**
     * selectById
     */
    Banner selectById(Integer id);

    /**
     * SelectAll
     */
    List<Banner> selectAll(Banner banner);
}
