package com.small.fruitandvagetablemallspringboot.service.Impl;

import com.small.fruitandvagetablemallspringboot.dao.ShoppingTrolleyMapper;
import com.small.fruitandvagetablemallspringboot.pojo.ShoppingTrolley;
import com.small.fruitandvagetablemallspringboot.result.Result;
import com.small.fruitandvagetablemallspringboot.result.ResultCodeEnum;
import com.small.fruitandvagetablemallspringboot.service.ShoppingTrolleyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 购物车信息表service实现
 *
 * @author GH
 * @date 2022-04-12 15:54
 */
@Service
public class ShoppingTrolleyServiceImpl implements ShoppingTrolleyService {
    //自动装配购物车mapper
    @Autowired
    ShoppingTrolleyMapper trolleyMapper;

    /**
     * 查询所有购物车信息
     *
     * @return
     */
    @Override
    public Result queryForShopTrolleyAndPersonCommodityInfo() {
        List<Map<String, Object>> maps = trolleyMapper.queryForShopTrolleyAndPersonCommodityInfo();

        return Result.ok().data("trolleyList", maps);
    }

    /**
     * 根据用户id查询对应购物车信息
     *
     * @return
     */
    @Override
    public Result queryForShopTrolleyAndPersonCommodityInfoById(Integer uid) {
        if (null == uid) {
            //uid为空值 抛出参数异常信息
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        List<Map<String, Object>> maps = trolleyMapper.queryForShopTrolleyAndPersonCommodityInfoById(uid);

        return Result.ok().data("trolleyList", maps);
    }

    /**
     * 添加一条购物车信息
     *
     * @return
     */
    @Override
    public Result addShoppingTrolley(ShoppingTrolley shoppingTrolley) {
        if (null == shoppingTrolley.getCommodityId() ||
                null == shoppingTrolley.getUserId() ||
                null == shoppingTrolley.getCommodityNumber()
        ) {
            //必填参数为空  返回参数异常信息
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        //根据用户id和商品id查到此用户购物车中是否已存在需添加的相同商品 如有则只需修改数量 如果没有 则添加整个商品信息
        ShoppingTrolley shoppingTrolley1 = trolleyMapper.queryForShopTrolleyByShopId
                (shoppingTrolley.getCommodityId(), shoppingTrolley.getUserId());

        System.out.println("原来商品====>"+shoppingTrolley1);
        if(null!=shoppingTrolley1){
            //此用户购物车中的商品存在了 只需要修改数量
            //商品数量++
            System.out.println("原来的数量为:"+shoppingTrolley1.getCommodityNumber());
            shoppingTrolley.setCommodityNumber(shoppingTrolley1.getCommodityNumber()+1);
            Integer integer = trolleyMapper.updateShoppingTrolleyByUserId(shoppingTrolley);
            if(null==integer){
                return  Result.error(ResultCodeEnum.FAIL);
            }else {
                return  Result.ok();
            }
        }
        Integer integer = trolleyMapper.addShoppingTrolley(shoppingTrolley);
        if (integer < 1) {
            //添加异常
            return Result.error(ResultCodeEnum.FAIL);
        }
        return Result.ok();
    }

    /**
     * 修改一条购物车信息
     *
     * @return
     */
    @Override
    public Result updateShoppingTrolleyById(ShoppingTrolley shoppingTrolley) {
        if (null == shoppingTrolley.getCommodityId() ||
                null == shoppingTrolley.getId() ||
                null == shoppingTrolley.getUserId() ||
                null == shoppingTrolley.getCommodityNumber()
        ) {
            //必填参数为空  返回参数异常信息
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        Integer integer = trolleyMapper.updateShoppingTrolleyById(shoppingTrolley);
        if (integer < 1) {
            //添加异常
            return Result.error(ResultCodeEnum.FAIL);
        }
        return Result.ok();
    }

    /**
     * 删除一条购物车信息
     *
     * @return
     */
    @Override
    public Result deleteShoppingTrolleyById(Integer id) {
        if (null == id) {
            //id为空值 抛出参数异常信息
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }

        Integer integer = trolleyMapper.deleteShoppingTrolleyById(id);
        if (null == integer) {
            return Result.error(ResultCodeEnum.FAIL);
        }
        return Result.ok();
    }

    /**
     * 根据用户id删除对应购物车信息
     * @param uid
     * @return
     */
    @Override
    public Result deleteShoppingTrolleyByUserId(Integer uid) {
        if (null == uid) {
            //id为空值 抛出参数异常信息
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }

        Integer integer = trolleyMapper.deleteShoppingTrolleyByUserId(uid);
        if (null == integer) {
            return Result.error(ResultCodeEnum.FAIL);
        }
        return Result.ok();
    }
}
