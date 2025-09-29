package com.small.fruitandvagetablemallspringboot.dao;

import com.small.fruitandvagetablemallspringboot.pojo.CommodityInformation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 商品信息mapper
 */
@Mapper
@Repository
public interface CommodityInformationMpper {

    /**
     * 关联查询 商品信息和商品分类信息 模糊查询
     *
     * @return
     */
    List<Map<String, Object>> queryForCommodityInfoAndClass(@Param("commodityClassId") Integer commodityClassId, @Param("search") String search
            , @Param("start") Integer start, @Param("pageSize") Integer pageSize);

    /**
     * 关联查询 商品信息和商品分类信息
     *
     * @return
     */
    List<Map<String, Object>> queryForCommodityInfo();


    /**
     * 根据id关联查询 商品信息和商品分类信息
     *
     * @return
     */
    List<Map<String, Object>> queryForCommodityInformationById(@Param("id") Integer id);

    /**
     * 查询当前商品信息条数
     *
     * @return
     */
    Integer queryForCommodityCount(@Param("commodityClassId") Integer commodityClassId
            , @Param("search") String search);

    /**
     * 添加商品信息
     *
     * @param commodityInformation
     * @return
     */
    Integer addCommodityInfo(CommodityInformation commodityInformation);

    /**
     * 更新商品信息
     *
     * @param commodityInformation
     * @return
     */
    Integer updateCommodityInfoById(CommodityInformation commodityInformation);

    /**
     * 删除商品信息
     *
     * @param id
     * @return
     */
    Integer deleteCommodityInfoById(@Param("id") Integer id);


    /**
     * 批量删除商品信息
     *
     * @param commodityIds
     * @return
     */
    Integer deleteCommodityInfoBatchById(@Param("commodityIds") Integer[] commodityIds);

}
