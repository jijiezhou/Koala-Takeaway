package com.example.controller;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.Result;
import com.example.common.enums.ResultCodeEnum;
import com.example.entity.Business;
import com.example.exception.CustomException;
import com.example.service.BusinessService;
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

    @PostMapping("/add")
    public Result add(@RequestBody Business business){
        //check if username and password empty
        if (ObjectUtil.isEmpty(business.getUsername()) || ObjectUtil.isEmpty(business.getPassword())){
            throw new CustomException(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        businessService.add(business);
        return Result.success();
    }
    @GetMapping("/selectAll")
    public Result selectAll(Business business){
        List<Business> list = businessService.selectAll(business);
        return Result.success(list);
    }
}
