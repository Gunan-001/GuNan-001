package com.small.fruitandvagetablemallspringboot.dao;

import com.small.fruitandvagetablemallspringboot.pojo.Slideshow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 轮播图mapper
 */
@Mapper
@Repository
public interface SlideShowMapper {
    /**
     * 关联查询 轮播图信息 商品信息
     * @return
     */
    List<Slideshow> queryForSlideshowAndCommodityInfo();


    /**
     * 关联查询 根据用户id查询轮播图信息,商品信息
     * @return
     */
    Slideshow queryForSlideshowAndCommodityInfoById(@Param("id") Integer id);

    /**
     * 添加轮播图信息
     * @param
     * @return
     */
    Integer addSlideshow(Slideshow slideshow);

    /**
     * 更新轮播图信息
     * @param
     * @return
     */
    Integer updateSlideshowById(Slideshow slideshow);

    /**
     * 根据轮播图id删除轮播图信息
     * @param id
     * @return
     */
    Integer deleteSlideshowById(@Param("id") Integer id);

    /**
     * 根据轮播图id批量删除轮播图信息
     * @param slideIds
     * @return
     */
    Integer deleteSlideshowBatchById(@Param("slideIds") Integer[] slideIds);
}
