package com.small.fruitandvagetablemallspringboot.service;

import com.small.fruitandvagetablemallspringboot.pojo.ShoppingStar;
import com.small.fruitandvagetablemallspringboot.result.Result;
import org.apache.ibatis.annotations.Param;



/**
 * 收藏信息表
 */
public interface ShoppingStarService {

    /**
     * 关联查询 收藏信息和用户信息商品信息
     * @return
     */
    Result queryForShoppingStarAndPersonInfo();


    /**
     * 关联查询 根据用户id查询收藏信息和用户信息商品信息
     * @return
     */
    Result queryForShoppingStarById(@Param("uid") Integer uid);

    /**
     * 添加收藏信息
     * @param
     * @return
     */
    Result addShoppingStar(ShoppingStar shoppingStar);

    /**
     * 更新收藏信息
     * @param
     * @return
     */
    Result updateShoppingStarById(ShoppingStar shoppingStar);

    /**
     * 根据收藏id删除收藏信息
     * @param id
     * @return
     */
    Result deleteShoppingStarById(@Param("id") Integer id);
}
