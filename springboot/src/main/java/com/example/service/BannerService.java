package com.example.service;

import com.example.entity.Banner;
import com.example.mapper.BannerMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @Classname BannerService
 * @Description TODO
 * @Author zjj
 * @Date 11/11/23 4:54 PM
 */
@Service
public class BannerService {
    @Resource
    private BannerMapper bannerMapper;

    /**
     * Add
     */
    public void add(Banner banner) {
        bannerMapper.insert(banner);
    }

    /**
     * Delete
     */
    public void deleteById(Integer id) {
        bannerMapper.deleteById(id);
    }


    /**
     * DeleteBatch
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            bannerMapper.deleteById(id);
        }
    }

    /**
     * Update
     */
    public void updateById(Banner banner) {
        bannerMapper.updateById(banner);
    }

    /**
     * selectById
     */
    public Banner selectById(Integer id) {
        return bannerMapper.selectById(id);
    }

    /**
     * selectAll
     */
    public List<Banner> selectAll(Banner banner) {
        return bannerMapper.selectAll(banner);
    }

    /**
     * selectByPage
     */
    public PageInfo<Banner> selectPage(Banner banner, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Banner> list = bannerMapper.selectAll(banner);
        return PageInfo.of(list);
    }
}
