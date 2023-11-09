package com.example.controller;

import com.example.common.Result;
import com.example.entity.Category;
import com.example.service.CategoryService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Classname CategoryController
 * @Description TODO
 * @Author zjj
 * @Date 11/8/23 8:35PM
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    /**
     * Add Category
     *
     * @param category
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody Category category) {
        categoryService.add(category);
        return Result.success();
    }

    /**
     * Delete Category
     *
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        categoryService.deleteById(id);
        return Result.success();
    }

    /**
     * DeleteBatch Category
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        categoryService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * Update Category
     *
     * @param Category
     * @return
     */
    @PutMapping("/update")
    public Result update(@RequestBody Category Category) {
        categoryService.updateById(Category);
        return Result.success();
    }

    /**
     * Select All Category
     *
     * @param category
     * @return
     */
    @GetMapping("/selectAll")
    public Result selectAll(Category category) {
        List<Category> list = categoryService.selectAll(category);
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
        Category category = categoryService.selectById(id);
        return Result.success(category);
    }

    /**
     * selectPage pagination
     */
    @GetMapping("/selectPage")
    public Result selectPage(Category category,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Category> page = categoryService.selectPage(category, pageNum, pageSize);
        return Result.success(page);
    }
}
