package com.example.mapper;

import com.example.entity.Category;

import java.util.List;

/**
 * @Classname CategoryMapper
 * @Description TODO
 * @Author zjj
 * @Date 11/8/23 8:39PM
 */

public interface CategoryMapper {
    /**
     * Insert
     * @param category
     * @return
     */
    int insert(Category category);

    /**
     * Delete
     * @param id
     * @return
     */
    int deleteById(Integer id);

    /**
     * update
     * @param category
     * @return
     */
    int updateById(Category category);

    /**
     * select by id
     * @param id
     * @return
     */
    Category selectById(Integer id);

    /**
     * selectAll
     * @param category
     * @return
     */
    List<Category> selectAll(Category category);

}
