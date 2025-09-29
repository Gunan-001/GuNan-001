package com.small.fruitandvagetablemallspringboot.service;

import com.small.fruitandvagetablemallspringboot.pojo.CommodityInformation;
import com.small.fruitandvagetablemallspringboot.result.Result;
import org.apache.ibatis.annotations.Param;

public interface CommoInfoService {
    /**
     * 关联查询 商品信息和商品分类信息
     * @return
     */
    Result queryForCommodityInfoAndClass(Integer commodityClassId,String search,
                                         Integer currentPage, Integer pageSize);

    /**
     * 关联查询 商品信息和商品分类信息
     *
     * @return
     */
    Result queryForCommodityInfo();


    /**
     * 根据id关联查询 商品信息和商品分类信息
     * @return
     */
    Result queryForCommodityInformationById(@Param("id") Integer id);

    /**
     * 添加商品信息
     * @param commodityInformation
     * @return
     */
    Result addCommodityInfo(CommodityInformation commodityInformation);

    /**
     * 更新商品信息
     * @param commodityInformation
     * @return
     */
    Result updateCommodityInfoById(CommodityInformation commodityInformation);

    /**
     * 删除商品信息
     * @param id
     * @return
     */
    Result deleteCommodityInfoById(@Param("id") Integer id);

    /**
     * 批量删除商品信息
     *
     * @param commodityIds
     * @return
     */
    Result deleteCommodityInfoBatchById(@Param("commodityIds") Integer[] commodityIds);

}
