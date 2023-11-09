package com.example.service;

import com.example.entity.Business;
import com.example.entity.Category;
import com.example.mapper.CategoryMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Classname CategoryService
 * @Description TODO
 * @Author zjj
 * @Date 11/8/23 8:37 PM
 */
@Service
public class CategoryService {
    @Resource
    CategoryMapper categoryMapper;

    public void add(Category category) {
        categoryMapper.insert(category);
    }

    public void deleteById(Integer id) {
        categoryMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id: ids){
            categoryMapper.deleteById(id);
        }
    }

    public void updateById(Category category) {
        categoryMapper.updateById(category);
    }

    public List<Category> selectAll(Category category) {
        return categoryMapper.selectAll(category);
    }

    public Category selectById(Integer id) {
        return categoryMapper.selectById(id);
    }

    public PageInfo<Category> selectPage(Category category, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Category> list = categoryMapper.selectAll(category);
        return PageInfo.of(list);
    }
}
