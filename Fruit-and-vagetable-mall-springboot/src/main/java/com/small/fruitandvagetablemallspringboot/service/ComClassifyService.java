package com.small.fruitandvagetablemallspringboot.service;

import com.small.fruitandvagetablemallspringboot.pojo.CommodityClassify;
import com.small.fruitandvagetablemallspringboot.result.Result;
import org.apache.ibatis.annotations.Param;

/**
 * 分类信息表service
 */
public interface ComClassifyService {

    /**
     * 根据id查询分类名称
     * @param
     * @return
     */
    public Result queryForComClassfyById(@Param("id") Integer id);


    /**
     * 关联查询查询所有商品对应的分类
     * @return
     */
    public Result queryForComClassfy();


    /**
     * 新增一条分类数据
     * @param commodityClassify
     * @return
     */
    public Result insertClassify(CommodityClassify commodityClassify);

    /**
     * 根据id修改分类
     * @param
     * @return
     */
    public Result updateClassById(CommodityClassify commodityClassify);



    /**
     * 根据id删除分类
     * @param
     * @return
     */
    public Result deleteClassById(@Param("id") Integer id);

    /**
     * 根据id批量删除分类
     * @param
     * @return
     */
    public Result deleteClassByBatchId(@Param("classIds") Integer[] classIds);
}
