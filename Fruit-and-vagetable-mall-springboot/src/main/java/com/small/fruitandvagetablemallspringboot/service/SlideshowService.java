package com.small.fruitandvagetablemallspringboot.service;

import com.small.fruitandvagetablemallspringboot.pojo.Slideshow;
import com.small.fruitandvagetablemallspringboot.result.Result;
import org.apache.ibatis.annotations.Param;

/**
 * 轮播图service
 */
public interface SlideshowService {
    /**
     * 关联查询 轮播图信息 商品信息
     * @return
     */
    Result queryForSlideshowAndCommodityInfo();


    /**
     * 关联查询 根据用户id查询轮播图信息,商品信息
     * @return
     */
    Result queryForSlideshowAndCommodityInfoById(@Param("id") Integer id);

    /**
     * 添加轮播图信息
     * @param
     * @return
     */
    Result addSlideshow(Slideshow slideshow);

    /**
     * 更新轮播图信息
     * @param
     * @return
     */
    Result updateSlideshowById(Slideshow slideshow);

    /**
     * 根据轮播图id删除轮播图信息
     * @param id
     * @return
     */
    Result deleteSlideshowById(@Param("id") Integer id);

    /**
     * 根据轮播图id批量删除轮播图信息
     * @param slideIds
     * @return
     */
    Result deleteSlideshowBatchById(@Param("slideIds") Integer[] slideIds);
}
