package com.small.fruitandvagetablemallspringboot.dao;

import com.small.fruitandvagetablemallspringboot.pojo.ShoppingTrolley;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 购物车信息mapper
 */
@Mapper
@Repository
public interface ShoppingTrolleyMapper {
    /**
     * 关联查询 购物车信息和用户信息 商品信息
     * @return
     */
    List<Map<String,Object>> queryForShopTrolleyAndPersonCommodityInfo();

    /**
     * 根据购物车主键查询对应信息
     * @return
     */
    ShoppingTrolley queryForShopTrolleyByShopId(@Param("commodityId") Integer commodityId,
                                                         @Param("userId") Integer userId);

    /**
     * 关联查询 根据用户id查询购物车信息,用户信息,商品信息
     * @return
     */
    List<Map<String,Object>> queryForShopTrolleyAndPersonCommodityInfoById(@Param("uid") Integer uid);

    /**
     * 添加购物车信息
     * @param
     * @return
     */
    Integer addShoppingTrolley(ShoppingTrolley shoppingTrolley);

    /**
     * 更新购物车信息
     * @param
     * @return
     */
    Integer updateShoppingTrolleyById(ShoppingTrolley shoppingTrolley);

    /**
     * 根据指定用户id 和商品id修改购物车中的商品数量 商品数量+1
     * @param shoppingTrolley
     * @return
     */
    Integer updateShoppingTrolleyByUserId(ShoppingTrolley shoppingTrolley);

    /**
     * 根据购物车id删除购物车信息
     * @param id
     * @return
     */
    Integer deleteShoppingTrolleyById(@Param("id") Integer id);


    /**
     * 根据用户信息userId删除购物车信息
     * @param uid
     * @return
     */
    Integer deleteShoppingTrolleyByUserId(@Param("uid") Integer uid);

    /**
     * 根据商品信息Id删除购物车信息
     * @param cid
     * @return
     */
    Integer deleteShoppingTrolleyByCommodityIds(@Param("cids") Integer[] cids);
}
