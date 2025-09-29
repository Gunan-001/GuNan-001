package com.small.fruitandvagetablemallspringboot.service.Impl;

import com.small.fruitandvagetablemallspringboot.dao.CommodityClassifyMapper;
import com.small.fruitandvagetablemallspringboot.pojo.CommodityClassify;
import com.small.fruitandvagetablemallspringboot.result.Result;
import com.small.fruitandvagetablemallspringboot.result.ResultCodeEnum;
import com.small.fruitandvagetablemallspringboot.service.ComClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author GH
 * @date 2022-04-12 15:52
 */
@Service
public class ComClassifyServiceImpl implements ComClassifyService {
    //自动装配classifyMapper
    @Autowired
    CommodityClassifyMapper classifyMapper;

    /**
     * 根据id查询对应商品分类信息
     *
     * @param id
     * @return
     */
    @Override
    public Result queryForComClassfyById(@RequestParam("id") Integer id) {
        if (null == id) {
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        CommodityClassify classify = classifyMapper.queryForComClassfyById(id);
        return Result.ok().data("classify", classify);
    }

    /**
     * 查询所有商品分类信息
     *
     * @return
     */
    @Override
    public Result queryForComClassfy() {
        List<CommodityClassify> commodityClassifies = classifyMapper.queryForComClassfy();
        if (null == commodityClassifies) {
            return Result.error(ResultCodeEnum.FAIL);
        }
        return Result.ok().data("comodityList", commodityClassifies);
    }

    /**
     * 插入一条商品分类信息
     *
     * @param commodityClassify
     * @return
     */
    @Override
    public Result insertClassify(CommodityClassify commodityClassify) {
        if (commodityClassify == null ||
                "".equals(commodityClassify.getCommoditTypeName().trim())
                || null == commodityClassify.getCommoditTypeName().trim()
        ) {
            //数据为空则返回错误信息
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        Integer integer = classifyMapper.insertClassify(commodityClassify);

        if(integer<1){
            //加入失败
            return Result.error(ResultCodeEnum.FAIL);
        }
        return Result.ok();
    }

    /**
     * 修改商品分类信息
     *
     * @param commodityClassify
     * @return
     */
    @Override
    public Result updateClassById(CommodityClassify commodityClassify) {
        if (commodityClassify.getId() == null) {
            //数据为空则返回错误信息
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        Integer integer = classifyMapper.updateClassById(commodityClassify);
        if(integer<1){
            //更改失败
            return Result.error(ResultCodeEnum.FAIL);
        }
        return Result.ok();
    }


    /**
     * 删除对应商品分类信息
     *
     * @param id
     * @return
     */
    @Override
    public Result deleteClassById(Integer id) {
        if(null==id){
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        Integer integer = classifyMapper.deleteClassById(id);
        if(integer<1){
            //删除失败 没找到对应id
            return Result.error(ResultCodeEnum.FAIL);
        }
        return Result.ok();
    }

    /**
     * 批量删除商品分类
     * @param classIds
     * @return
     */
    @Override
    public Result deleteClassByBatchId(Integer[] classIds) {
        if(null==classIds){
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        Integer integer = classifyMapper.deleteClassByBatchId(classIds);
        if(integer<classIds.length){
            return Result.error(ResultCodeEnum.FAIL);
        }
        return Result.ok();
    }
}
