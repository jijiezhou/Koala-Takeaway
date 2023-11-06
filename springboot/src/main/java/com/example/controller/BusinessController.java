package com.example.controller;

import com.example.common.Result;
import com.example.entity.Business;
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
        businessService.add(business);
        return Result.success();
    }
    @GetMapping("/selectAll")
    public Result selectAll(){
        List<Business> list = businessService.selectAll();
        return Result.success(list);
    }
}
