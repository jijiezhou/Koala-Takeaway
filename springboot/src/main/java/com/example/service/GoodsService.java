package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Category;
import com.example.entity.Goods;
import com.example.entity.Goods;
import com.example.mapper.GoodsMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Classname GoodsService
 * @Description TODO
 * @Author zjj
 * @Date 11/10/23 10:05PM
 */
@Service
public class GoodsService {
    @Resource
    GoodsMapper goodsMapper;

    @Resource
    BusinessService businessService;

    @Resource
    CategoryService categoryService;

    /**
     * add
     * @param goods
     */
    public void add(Goods goods) {
        //check business auth(make sure current business status pass)
        businessService.checkBusinessAuth();

        //select category data
        Category category = categoryService.selectById(goods.getCategoryId());
        if (ObjectUtil.isNotEmpty(category)){
            goods.setBusinessId(category.getBusinessId());
        }

        goodsMapper.insert(goods);
    }

    /**
     * deleteById
     * @param id
     */
    public void deleteById(Integer id) {
        //check business auth(make sure current business status pass)
        businessService.checkBusinessAuth();
        goodsMapper.deleteById(id);
    }

    /**
     * deletebatch
     * @param ids
     */
    public void deleteBatch(List<Integer> ids) {
        //check business auth(make sure current business status pass)
        businessService.checkBusinessAuth();
        for (Integer id: ids){
            goodsMapper.deleteById(id);
        }
    }

    /**
     * updateById
     * @param goods
     */
    public void updateById(Goods goods) {
        //check business auth(make sure current business status pass)
        businessService.checkBusinessAuth();
        goodsMapper.updateById(goods);
    }

    public List<Goods> selectAll(Goods goods) {
        //get current login user
        Account currentUser = TokenUtils.getCurrentUser();
        String role = currentUser.getRole();
        //check is user is business, then it can only select its info
        if (role.equals(RoleEnum.BUSINESS.name())){
            goods.setBusinessId(currentUser.getId());
        }
        return goodsMapper.selectAll(goods);
    }

    public Goods selectById(Integer id) {
        return goodsMapper.selectById(id);
    }

    public PageInfo<Goods> selectPage(Goods goods, Integer pageNum, Integer pageSize) {
        //get current login user
        Account currentUser = TokenUtils.getCurrentUser();
        String role = currentUser.getRole();
        //check is user is business, then it can only select its info
        if (role.equals(RoleEnum.BUSINESS.name())){
            goods.setBusinessId(currentUser.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Goods> list = goodsMapper.selectAll(goods);
        return PageInfo.of(list);
    }
}
