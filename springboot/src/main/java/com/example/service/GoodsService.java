package com.example.service;

import com.example.entity.Goods;
import com.example.entity.Goods;
import com.example.mapper.GoodsMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Classname GoodsService
 * @Description TODO
 * @Author zjj
 * @Date 11/10/23 10:05PM
 */
public class GoodsService {
    @Resource
    GoodsMapper goodsMapper;

    /**
     * add
     * @param goods
     */
    public void add(Goods goods) {
        goodsMapper.insert(goods);
    }

    /**
     * deleteById
     * @param id
     */
    public void deleteById(Integer id) {
        goodsMapper.deleteById(id);
    }

    /**
     * deletebatch
     * @param ids
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id: ids){
            goodsMapper.deleteById(id);
        }
    }

    /**
     * updateById
     * @param goods
     */
    public void updateById(Goods goods) {
        goodsMapper.updateById(goods);
    }

    public List<Goods> selectAll(Goods goods) {
        return goodsMapper.selectAll(goods);
    }

    public Goods selectById(Integer id) {
        return goodsMapper.selectById(id);
    }

    public PageInfo<Goods> selectPage(Goods goods, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Goods> list = goodsMapper.selectAll(goods);
        return PageInfo.of(list);
    }
}
