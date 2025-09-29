package com.small.fruitandvagetablemallspringboot.controller;

import com.small.fruitandvagetablemallspringboot.pojo.ShoppingAddress;
import com.small.fruitandvagetablemallspringboot.result.Result;
import com.small.fruitandvagetablemallspringboot.service.ShoppingAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 收货地址信息controller
 *
 * @author GH
 * @date 2022-04-14 17:42
 */
@RestController
@RequestMapping("/address")

public class ShoppingAddressController {
    //自动装配收货地址service
    @Autowired
    ShoppingAddressService service;

    /**
     * 查询所有收货地址信息
     *
     * @return result
     */

    @GetMapping("/query-ForShoppingAddressAnd-PersonInfo")
    public Result queryForShoppingAddressAndPersonInfo() {
        return service.queryForShoppingAddressAndPersonInfo();
    }

    /**
     * 根据id查询对应收货地址信息
     *
     * @param uid
     * @return
     */

    @GetMapping("/query-ForShoppingAddress-ById/{uid}")
    public Result queryForShoppingAddressById(@PathVariable("uid") Integer uid) {
        return service.queryForShoppingAddressById(uid);
    }

    /**
     * 添加一条收货地址信息
     *
     * @param shoppingAddress
     * @return
     */

    @PostMapping("/add-ShoppingAddress")
    public Result addShoppingAddress(@RequestBody(required = true) ShoppingAddress shoppingAddress) {

        return service.addShoppingAddress(shoppingAddress);
    }

    /**
     * 修改对应收货地址信息
     *
     * @param shoppingAddress
     * @return
     */

    @PutMapping("/update-ShoppingAddress-ById")
    public Result updateShoppingAddressById(@RequestBody(required = true) ShoppingAddress shoppingAddress) {
        return service.updateShoppingAddressById(shoppingAddress);
    }


    /**
     * 删除对应收货地址信息
     *
     * @param id
     * @return
     */

    @DeleteMapping("/delete-ShoppingAddress-ById/{id}")
    public Result deleteShoppingAddressById(@PathVariable("id") Integer id) {
        return service.deleteShoppingAddressById(id);
    }
}