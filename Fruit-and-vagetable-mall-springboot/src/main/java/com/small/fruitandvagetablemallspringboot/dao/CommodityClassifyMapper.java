package com.small.fruitandvagetablemallspringboot.dao;

import com.small.fruitandvagetablemallspringboot.pojo.CommodityClassify;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品分类mapper
 */
@Mapper
@Repository
public interface CommodityClassifyMapper {
    /**
     * 根据id查询分类名称
     * @param
     * @return
     */
    public CommodityClassify queryForComClassfyById(@Param("id") Integer id);


    /**
     * 关联查询查询所有商品对应的分类
     * @return
     */
    public  List<CommodityClassify> queryForComClassfy();


    /**
     * 新增一条分类数据
     * @param commodityClassify
     * @return
     */
    public Integer insertClassify(CommodityClassify commodityClassify);

    /**
     * 根据id修改分类
     * @param
     * @return
     */
    public Integer updateClassById(CommodityClassify commodityClassify);



    /**
     * 根据id删除分类
     * @param
     * @return
     */
    public Integer deleteClassById(@Param("id") Integer id);

    /**
     * 根据id批量删除分类
     * @param
     * @return
     */
    public Integer deleteClassByBatchId(@Param("classIds") Integer[] classIds);
}
