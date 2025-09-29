package com.small.fruitandvagetablemallspringboot.controller;

import com.small.fruitandvagetablemallspringboot.pojo.Slideshow;
import com.small.fruitandvagetablemallspringboot.result.Result;
import com.small.fruitandvagetablemallspringboot.service.SlideshowService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 轮播图controller
 *
 * @author GH
 * @date 2022-04-14 19:24
 */
@RestController
@RequestMapping("/slideShow")
public class SlideShowController {
    //自动装配轮播图service
    @Autowired
    SlideshowService service;

    /**
     * 查询所有轮播图信息
     *
     * @return result
     */
    @GetMapping("/query-ForSlideshowInfo")
    public Result queryForSlideshowAndCommodityInfo() {
        return service.queryForSlideshowAndCommodityInfo();
    }

    /**
     * 根据id查询对应轮播图信息
     *
     * @param id
     * @return
     */

    @GetMapping("/query-ForSlideshowAndCommodityInfo-ById/{id}")
     public Result queryForSlideshowAndCommodityInfoById(@PathVariable("id") Integer id) {
        return service.queryForSlideshowAndCommodityInfoById(id);
    }

    /**
     * 添加一条轮播图信息
     *
     * @param slideshow
     * @return
     */

    @PostMapping("/add-Slideshow")
    public Result addSlideshow(@RequestBody(required = true) Slideshow slideshow) {

        return service.addSlideshow(slideshow);
    }

    /**
     * 修改对应轮播图信息
     *
     * @param slideshow
     * @return
     */

    @PutMapping("/update-Slideshow-ById")
    public Result updateSlideshowById(@RequestBody(required = true) Slideshow slideshow) {
        return service.updateSlideshowById(slideshow);
    }


    /**
     * 删除对应轮播图信息
     *
     * @param id
     * @return
     */

    @DeleteMapping("/delete-Slideshow-ById/{id}")
     public Result deleteSlideshowById(@PathVariable("id") Integer id) {
        return service.deleteSlideshowById(id);
    }


    /**
     * 批量删除对应轮播图信息
     *
     * @param slideIds
     * @return
     */

    @PostMapping("/delete-SlideshowBatch-ById")
    public Result deleteSlideshowBatchById(@RequestBody(required = true) Integer[] slideIds) {
        return service.deleteSlideshowBatchById(slideIds);
    }
}
