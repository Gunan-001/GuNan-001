package com.small.fruitandvagetablemallspringboot.service;

import com.small.fruitandvagetablemallspringboot.pojo.ShoppingTrolley;
import com.small.fruitandvagetablemallspringboot.result.Result;
import org.apache.ibatis.annotations.Param;


/**
 * 购物车信息表service接口
 */
public interface ShoppingTrolleyService {
    /**
     * 关联查询 购物车信息和用户信息 商品信息
     * @return
     */
   Result queryForShopTrolleyAndPersonCommodityInfo();


    /**
     * 关联查询 根据用户id查询购物车信息,用户信息,商品信息
     * @return
     */
    Result queryForShopTrolleyAndPersonCommodityInfoById(@Param("uid") Integer uid);

    /**
     * 添加购物车信息
     * @param
     * @return
     */
    Result addShoppingTrolley(ShoppingTrolley shoppingTrolley);

    /**
     * 更新购物车信息
     * @param
     * @return
     */
    Result updateShoppingTrolleyById(ShoppingTrolley shoppingTrolley);

    /**
     * 根据购物车id删除购物车信息
     * @param id
     * @return
     */
    Result deleteShoppingTrolleyById(@Param("id") Integer id);


    /**
     * 根据用户信息userId删除购物车信息
     * @param uid
     * @return
     */
    Result deleteShoppingTrolleyByUserId(@Param("uid") Integer uid);
}
