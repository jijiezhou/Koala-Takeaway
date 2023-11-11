package com.example.mapper;

import com.example.entity.Goods;

import java.util.List;

/**
 * @Classname GoodsMapper
 * @Description TODO
 * @Author zjj
 * @Date 11/10/23 10:03 PM
 */
public interface GoodsMapper {
    /**
     * Insert
     *
     * @param goods
     * @return
     */
    int insert(Goods goods);

    /**
     * Delete
     *
     * @param id
     * @return
     */
    int deleteById(Integer id);

    /**
     * update
     *
     * @param goods
     * @return
     */
    int updateById(Goods goods);

    /**
     * select by id
     *
     * @param id
     * @return
     */
    Goods selectById(Integer id);

    /**
     * selectAll
     *
     * @param goods
     * @return
     */
    List<Goods> selectAll(Goods goods);
}
