package com.example.controller;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.Result;
import com.example.common.enums.ResultCodeEnum;
import com.example.entity.Banner;
import com.example.exception.CustomException;
import com.example.service.BannerService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Classname BannerController
 * @Description TODO
 * @Author zjj
 * @Date 11/11/23 4:50 PM
 */
@RestController
@RequestMapping("/banner")
public class BannerController {
    @Resource
    private BannerService bannerService;

    /**
     * Add Banner
     *
     * @param banner
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody Banner banner) {
        bannerService.add(banner);
        return Result.success();
    }

    /**
     * Delete Banner
     *
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        bannerService.deleteById(id);
        return Result.success();
    }

    /**
     * DeleteBatch Banner
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        bannerService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * Update Banner
     *
     * @param banner
     * @return
     */
    @PutMapping("/update")
    public Result update(@RequestBody Banner banner) {
        bannerService.updateById(banner);
        return Result.success();
    }

    /**
     * Select All Banner
     *
     * @param banner
     * @return
     */
    @GetMapping("/selectAll")
    public Result selectAll(Banner banner) {
        List<Banner> list = bannerService.selectAll(banner);
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
        Banner banner = bannerService.selectById(id);
        return Result.success(banner);
    }

    /**
     * selectPage pagination
     */
    @GetMapping("/selectPage")
    public Result selectPage(Banner banner,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Banner> page = bannerService.selectPage(banner, pageNum, pageSize);
        return Result.success(page);
    }
}
