package com.small.fruitandvagetablemallspringboot.controller;

import com.small.fruitandvagetablemallspringboot.pojo.ShoppingStar;
import com.small.fruitandvagetablemallspringboot.result.Result;
import com.small.fruitandvagetablemallspringboot.service.ShoppingStarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 收藏信息controller
 * @author GH
 * @date 2022-04-14 18:46
 */

@RestController
@RequestMapping("/star")

public class ShoppingStarController {
    //自动装配收藏service
    @Autowired
    ShoppingStarService service;

    /**
     * 查询所有收藏信息
     *
     * @return result
     */
    @GetMapping("/query-ForShoppingStarAnd-PersonInfo")
    public Result queryForShoppingStarAndPersonInfo() {
        return service.queryForShoppingStarAndPersonInfo();
    }

    /**
     * 根据id查询对应收藏信息
     *
     * @param uid
     * @return
     */

    @GetMapping("/query-ForShoppingStarById/{uid}")
    public Result queryForShoppingStarById(@PathVariable("uid") Integer uid) {
        return service.queryForShoppingStarById(uid);
    }

    /**
     * 添加一条收藏信息
     *
     * @param shoppingStar
     * @return
     */

    @PostMapping("/add-ShoppingStar")
    public Result addShoppingStar(@RequestBody(required = true) ShoppingStar shoppingStar) {

        return service.addShoppingStar(shoppingStar);
    }

    /**
     * 修改对应收藏信息
     *
     * @param shoppingStar
     * @return
     */
    @PutMapping("/update-ShoppingStar-ById")
    public Result updateShoppingStarById(@RequestBody(required = true) ShoppingStar shoppingStar ) {
        return service.updateShoppingStarById(shoppingStar);
    }


    /**
     * 删除对应收藏信息
     *
     * @param id
     * @return
     */
    @DeleteMapping("/delete-ShoppingStar-ById/{id}")
    public Result deleteShoppingStarById(@PathVariable("id") Integer id) {
        return service.deleteShoppingStarById(id);
    }

}
