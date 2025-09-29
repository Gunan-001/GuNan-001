package com.small.fruitandvagetablemallspringboot.service;

import com.small.fruitandvagetablemallspringboot.pojo.ShoppingAddress;
import com.small.fruitandvagetablemallspringboot.result.Result;
import org.apache.ibatis.annotations.Param;

public interface ShoppingAddressService {
    /**
     * 关联查询 收货信息和用户信息
     * @return
     */
   Result queryForShoppingAddressAndPersonInfo();


    /**
     * 关联查询 根据用户id查询收货信息和用户信息
     * @return
     */
    Result queryForShoppingAddressById(@Param("uid") Integer uid);

    /**
     * 添加收货信息
     * @param
     * @return
     */
    Result addShoppingAddress(ShoppingAddress shoppingAddress);

    /**
     * 更新收货信息
     * @param
     * @return
     */
    Result updateShoppingAddressById(ShoppingAddress shoppingAddress);


    /**
     * 根据收货id删除收货信息
     * @param id
     * @return
     */
    Result deleteShoppingAddressById(@Param("id") Integer id);
}
