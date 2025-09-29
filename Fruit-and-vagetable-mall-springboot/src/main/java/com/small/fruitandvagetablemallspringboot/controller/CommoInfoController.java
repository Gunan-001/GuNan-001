package com.small.fruitandvagetablemallspringboot.controller;

import com.small.fruitandvagetablemallspringboot.pojo.CommodityInformation;
import com.small.fruitandvagetablemallspringboot.result.Result;
import com.small.fruitandvagetablemallspringboot.service.CommoInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 商品信息controller
 *
 * @author GH
 * @date 2022-04-12 19:49
 */
@RestController
@RequestMapping("/commoInfo")

public class CommoInfoController {
    //自动装配商品service
    @Autowired
    CommoInfoService service;

    /**
     * 查询所有商品信息
     *
     * @return result
     */
    @GetMapping("/query-ForCommodityInfoAndClass")

    public Result queryForCommodityInfoAndClass(@RequestParam(value = "commodityClassId", required = false) Integer commodityClassId,
                                                @RequestParam(value = "search", required = false) String search,
                                                @RequestParam(value = "currentPage", required = false) Integer currentPage,
                                                @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return service.queryForCommodityInfoAndClass(commodityClassId, search, currentPage, pageSize);
    }

    /**
     * 查询全部
     *
     * @return
     */

    @GetMapping("/query-ForCommodityInfo")
    public Result queryForCommodityInfo() {
        return service.queryForCommodityInfo();
    }

    /**
     * 根据id查询对应商品信息
     *
     * @param id
     * @return
     */

    @GetMapping("/query-ForCommodityInformation-ById/{id}")
      public Result queryForCommodityInformationById(@PathVariable("id") Integer id) {
        return service.queryForCommodityInformationById(id);
    }


    /**
     * 添加一条商品信息
     *
     * @param commodityInformation
     * @return
     */

    @PostMapping("/add-CommodityInfo")
    public Result addCommodityInfo(@RequestBody(required = true) CommodityInformation commodityInformation) {

        return service.addCommodityInfo(commodityInformation);
    }

    /**
     * 修改对应商品信息
     *
     * @param commodityInformation
     * @return
     */

    @PutMapping("/update-CommodityInfo-ById")
      public Result updateCommodityInfoById(@RequestBody(required = true) CommodityInformation commodityInformation) {
        return service.updateCommodityInfoById(commodityInformation);
    }


    /**
     * 删除对应商品信息
     *
     * @param id
     * @return
     */

    @DeleteMapping("/delete-CommodityInfo-ById/{id}")

    public Result deleteCommodityInfoById(@PathVariable("id") Integer id) {
        return service.deleteCommodityInfoById(id);
    }

    /**
     * 批量删除对应商品信息
     *
     * @param commodityIds
     * @return
     */

    @PostMapping("/delete-CommodityInfoBatch-ById")

    public Result deleteCommodityInfoBatchById(@RequestBody(required = false) Integer[] commodityIds) {
        System.out.println("commodityIds======>"+commodityIds);
        return service.deleteCommodityInfoBatchById(commodityIds);
    }

}
