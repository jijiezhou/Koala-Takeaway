package com.example.service;

import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Business;
import com.example.entity.Category;
import com.example.mapper.CategoryMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jdk.nashorn.internal.parser.Token;
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

    @Resource
    BusinessService businessService;

    /**
     * add
     * @param category
     */
    public void add(Category category) {
        //check business auth
        businessService.checkBusinessAuth();
        Account currentUser = TokenUtils.getCurrentUser();
        //if current is business, set default businessID
        if (currentUser.getRole().equals(RoleEnum.BUSINESS.name())){
            category.setBusinessId(currentUser.getId());
        }
        categoryMapper.insert(category);
    }

    /**
     * deleteById
     * @param id
     */
    public void deleteById(Integer id) {
        //check business auth
        businessService.checkBusinessAuth();
        categoryMapper.deleteById(id);
    }

    /**
     * deletebatch
     * @param ids
     */
    public void deleteBatch(List<Integer> ids) {
        //check business auth
        businessService.checkBusinessAuth();
        for (Integer id: ids){
            categoryMapper.deleteById(id);
        }
    }

    /**
     * updateById
     * @param category
     */
    public void updateById(Category category) {
        //check business auth
        businessService.checkBusinessAuth();
        categoryMapper.updateById(category);
    }

    public List<Category> selectAll(Category category) {
        //get current login user
        Account currentUser = TokenUtils.getCurrentUser();
        String role = currentUser.getRole();
        //check is user is business, then it can only select its info
        if (role.equals(RoleEnum.BUSINESS.name())){
            category.setBusinessId(currentUser.getId());
        }
        return categoryMapper.selectAll(category);
    }

    public Category selectById(Integer id) {
        return categoryMapper.selectById(id);
    }

    public PageInfo<Category> selectPage(Category category, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        //get current login user
        Account currentUser = TokenUtils.getCurrentUser();
        String role = currentUser.getRole();
        //check is user is business, then it can only select its info
        if (role.equals(RoleEnum.BUSINESS.name())){
            category.setBusinessId(currentUser.getId());
        }
        List<Category> list = categoryMapper.selectAll(category);
        return PageInfo.of(list);
    }
}
