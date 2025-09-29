package com.small.fruitandvagetablemallspringboot.controller;

import com.small.fruitandvagetablemallspringboot.pojo.ShoppingTrolley;
import com.small.fruitandvagetablemallspringboot.result.Result;
import com.small.fruitandvagetablemallspringboot.service.ShoppingTrolleyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 购物车信息controller层
 *
 * @author GH
 * @date 2022-04-14 19:03
 */
@RestController
@RequestMapping("/trolley")

public class ShoppingTrolleyController {
    //自动装配购物车service
    @Autowired
    ShoppingTrolleyService service;

    /**
     * 查询所有购物车信息
     *
     * @return result
     */

    @GetMapping("/query-ForShopTrolleyAnd-Person-CommodityInfo")
    public Result queryForShopTrolleyAndPersonCommodityInfo() {
        return service.queryForShopTrolleyAndPersonCommodityInfo();
    }

    /**
     * 根据id查询对应购物车信息
     *
     * @param uid
     * @return
     */

    @GetMapping("/query-ForShopTrolleyAnd-PersonCommodityInfo-ById/{uid}")
      public Result queryForShopTrolleyAndPersonCommodityInfoById(@PathVariable("uid") Integer uid) {
        return service.queryForShopTrolleyAndPersonCommodityInfoById(uid);
    }

    /**
     * 添加一条购物车信息
     *
     * @param shoppingTrolley
     * @return
     */

    @PostMapping("/add-ShoppingTrolley")
    public Result addShoppingTrolley(@RequestBody(required = true) ShoppingTrolley shoppingTrolley) {

        return service.addShoppingTrolley(shoppingTrolley);
    }

    /**
     * 修改对应购物车信息
     *
     * @param shoppingTrolley
     * @return
     */

    @PutMapping("/update-ShoppingTrolley-ById")
    public Result updateShoppingTrolleyById(@RequestBody(required = true) ShoppingTrolley shoppingTrolley) {
        return service.updateShoppingTrolleyById(shoppingTrolley);
    }


    /**
     * 根据购物车主键删除对应购物车信息
     *
     * @param id
     * @return
     */

    @DeleteMapping("/delete-ShoppingTrolley-ById/{id}")
    public Result deleteShoppingTrolleyById(@PathVariable("id") Integer id) {
        return service.deleteShoppingTrolleyById(id);
    }

    /**
     * 根据userId删除对应购物车信息
     *
     * @param uid
     * @return
     */

    @DeleteMapping("/delete-ShoppingTrolleyBy-UserId/{uid}")
    public Result deleteShoppingTrolleyByUserId(@PathVariable("uid") Integer uid) {
        return service.deleteShoppingTrolleyByUserId(uid);
    }

}
