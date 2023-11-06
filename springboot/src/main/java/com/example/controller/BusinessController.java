package com.example.controller;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.Result;
import com.example.common.enums.ResultCodeEnum;
import com.example.entity.Business;
import com.example.exception.CustomException;
import com.example.service.BusinessService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Classname BusinessController
 * @Description http controller related business
 * @Author zjj
 * @Date 11/5/23 4:33PM
 */
@RestController
@RequestMapping("/business")
public class BusinessController {
    @Resource
    private BusinessService businessService;

    /**
     * Add Business
     *
     * @param business
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody Business business) {
        //check if username and password empty
        if (ObjectUtil.isEmpty(business.getUsername()) || ObjectUtil.isEmpty(business.getPassword())) {
            throw new CustomException(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        businessService.add(business);
        return Result.success();
    }

    /**
     * Delete Business
     *
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        businessService.deleteById(id);
        return Result.success();
    }

    /**
     * DeleteBatch Business
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        businessService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * Update Business
     *
     * @param business
     * @return
     */
    @PutMapping("/update")
    public Result update(@RequestBody Business business) {
        businessService.updateById(business);
        return Result.success();
    }

    /**
     * Select All Business
     *
     * @param business
     * @return
     */
    @GetMapping("/selectAll")
    public Result selectAll(Business business) {
        List<Business> list = businessService.selectAll(business);
        return Result.success(list);
    }

    /**
     * Select By Id
     *
     * @param id
     * @return
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Business business = businessService.selectById(id);
        return Result.success(business);
    }

    /**
     * selectPage pagination
     */
    @GetMapping("/selectPage")
    public Result selectPage(Business business,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Business> page = businessService.selectPage(business, pageNum, pageSize);
        return Result.success(page);
    }
}
