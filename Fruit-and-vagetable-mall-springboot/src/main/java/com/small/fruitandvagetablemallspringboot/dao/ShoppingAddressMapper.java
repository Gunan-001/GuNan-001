package com.small.fruitandvagetablemallspringboot.dao;

import com.small.fruitandvagetablemallspringboot.pojo.ShoppingAddress;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 收货地址mapper
 */
@Mapper
@Repository
public interface ShoppingAddressMapper {
    /**
     * 关联查询 收货信息和用户信息
     * @return
     */
    List<Map<String,Object>> queryForShoppingAddressAndPersonInfo();


    /**
     * 关联查询 根据用户id查询收货信息和用户信息
     * @return
     */
    List<Map<String,Object>> queryForShoppingAddressById(@Param("uid") Integer uid);

    /**
     * 添加收货信息
     * @param
     * @return
     */
    Integer addShoppingAddress(ShoppingAddress shoppingAddress);

    /**
     * 更新收货信息
     * @param
     * @return
     */
    Integer updateShoppingAddressById(ShoppingAddress shoppingAddress);


    /**
     * 根据userId更新收货信息
     * @param
     * @return
     */
    Integer updateShoppingAddressByUserId(@Param("uid") Integer uid);

    /**
     * 根据收货id删除收货信息
     * @param id
     * @return
     */
    Integer deleteShoppingAddressById(@Param("id") Integer id);
}
