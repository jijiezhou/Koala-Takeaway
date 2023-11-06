package com.example.controller;

import com.example.common.Result;
import com.example.service.BusinessService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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

    @GetMapping("/selectAll")
    public Result selectAll(){
        String result = businessService.selectAll();
        return Result.success(result);
    }
}
