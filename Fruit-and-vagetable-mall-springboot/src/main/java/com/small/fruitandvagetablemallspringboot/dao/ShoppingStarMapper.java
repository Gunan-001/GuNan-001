package com.small.fruitandvagetablemallspringboot.dao;

import com.small.fruitandvagetablemallspringboot.pojo.ShoppingStar;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 收藏信息mapper
 */
@Mapper
@Repository
public interface ShoppingStarMapper {

    /**
     * 关联查询 收藏信息和用户信息商品信息
     * @return
     */
    List<Map<String,Object>> queryForShoppingStarAndPersonInfo();


    /**
     * 关联查询 根据用户id查询收藏信息和用户信息商品信息
     * @return
     */
    List<Map<String,Object>> queryForShoppingStarById(@Param("uid") Integer uid);

    /**
     * 添加收藏信息
     * @param
     * @return
     */
    Integer addShoppingStar(ShoppingStar shoppingStar);

    /**
     * 更新收藏信息
     * @param
     * @return
     */
    Integer updateShoppingStarById(ShoppingStar shoppingStar);

    /**
     * 根据收藏id删除收藏信息
     * @param id
     * @return
     */
    Integer deleteShoppingStarById(@Param("id") Integer id);
}
