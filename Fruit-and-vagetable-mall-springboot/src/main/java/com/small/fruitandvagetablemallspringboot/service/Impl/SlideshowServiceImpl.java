package com.small.fruitandvagetablemallspringboot.service.Impl;

import com.small.fruitandvagetablemallspringboot.dao.SlideShowMapper;
import com.small.fruitandvagetablemallspringboot.pojo.Slideshow;
import com.small.fruitandvagetablemallspringboot.result.Result;
import com.small.fruitandvagetablemallspringboot.result.ResultCodeEnum;
import com.small.fruitandvagetablemallspringboot.service.SlideshowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 轮播图service实现
 */
@Service
public class SlideshowServiceImpl implements SlideshowService {

    //自动装配轮播图mapper
    @Autowired
    SlideShowMapper slideshowMapper;

    /**
     * 查询所有轮播图信息
     *
     * @return
     */
    @Override
    public Result queryForSlideshowAndCommodityInfo() {
        List<Slideshow> slideshows = slideshowMapper.queryForSlideshowAndCommodityInfo();

        return Result.ok().data("slideList", slideshows);
    }

    /**
     * 根据id查询对应轮播图信息
     *
     * @param id
     * @return
     */
    @Override
    public Result queryForSlideshowAndCommodityInfoById(Integer id) {
        if (null == id) {
            //参数异常 主键不能为空 返回异常信息
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        Slideshow slideshow = slideshowMapper.queryForSlideshowAndCommodityInfoById(id);
        return Result.ok().data("slideshow", slideshow);
    }

    /**
     * 添加一条轮播图信息
     *
     * @return
     */
    @Override
    public Result addSlideshow(Slideshow slideshow) {
        if (null == slideshow.getName() || null == slideshow.getPath()

        ) {
            //必填参数为空 返回异常信息
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        Integer integer = slideshowMapper.addSlideshow(slideshow);
        if (integer < 1) {
            return Result.error(ResultCodeEnum.FAIL);
        }
        return Result.ok();
    }

    /**
     * 修改一条轮播图信息
     *
     * @return
     */
    @Override
    public Result updateSlideshowById(Slideshow slideshow) {
        if (null == slideshow.getName() || null == slideshow.getPath()
              || null == slideshow.getId()
        ) {
            //必填参数为空 返回异常信息
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        Integer integer = slideshowMapper.updateSlideshowById(slideshow);
        if (integer < 1) {
            return Result.error(ResultCodeEnum.FAIL);
        }
        return Result.ok();
    }

    /**
     * 删除对应轮播图信息
     *
     * @return
     */
    @Override
    public Result deleteSlideshowById(Integer id) {
        if (null == id) {
            //参数异常 主键不能为空 返回异常信息
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        Integer integer = slideshowMapper.deleteSlideshowById(id);
        if (integer < 1) {
            return Result.error(ResultCodeEnum.FAIL);
        }
        return Result.ok();
    }

    /**
     * 批量删除轮播图
     * @param slideIds
     * @return
     */
    @Override
    public Result deleteSlideshowBatchById(Integer[] slideIds) {
        if(null==slideIds){
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        Integer integer = slideshowMapper.deleteSlideshowBatchById(slideIds);
        if(integer<slideIds.length){
            return Result.error(ResultCodeEnum.FAIL);
        }
        return Result.ok();
    }
}
