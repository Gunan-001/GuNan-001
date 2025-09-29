package com.small.fruitandvagetablemallspringboot.controller;

import com.small.fruitandvagetablemallspringboot.pojo.CommodityClassify;
import com.small.fruitandvagetablemallspringboot.result.Result;
import com.small.fruitandvagetablemallspringboot.service.ComClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 商品分类controller
 * @author GH
 * @date 2022-04-13 19:16
 */
@RestController
@RequestMapping("/commoClassify")
//@Api(tags = "商品分类")
public class CommoClassifyController {

    @Autowired
    ComClassifyService service;
    /**
     * 查询所有商品分类
     *
     * @return result
     */
//    @ApiOperation("查询所有商品分类信息")
    @GetMapping("/query_ForComClassfy")
    public Result queryForComClassfy() {
        return   service.queryForComClassfy();
    }

    /**
     * 根据id查询对应商品信息
     *
     * @param id
     * @return
     */
//    @ApiOperation("根据ID查询分类信息")
    @GetMapping("/query-ForComClassfy-ById/{id}")
//    @ApiImplicitParam(value = "商品分类表主键",name = "id",required = true,paramType = "path",dataType = "int")
    public Result queryForComClassfyById(@PathVariable("id") Integer id) {
        return service.queryForComClassfyById(id);
    }

    /**
     * 添加一条商品信息
     * @param commodityClassify
     * @return
     */
//    @ApiOperation("添加一条商品分类信息")
    @PostMapping("/insert-Classify")
//    @ApiImplicitParam(value = "商品分类实体", name = "CommodityClassify", paramType = "body",required = true,dataType = "commodityClassify")
    public Result insertClassify(@RequestBody(required = true) CommodityClassify commodityClassify) {
        return service.insertClassify(commodityClassify);
    }

    /**
     * 修改对应商品信息
     * @param commodityClassify
     * @return
     */
//    @ApiOperation("修改对应商品分类信息")
    @PutMapping("/update-ClassById")
//    @ApiImplicitParam(value = "商品分类实体", name = "CommodityClassify", paramType = "body",required = true,dataType = "commodityClassify")
    public Result updateClassById(@RequestBody(required = true) CommodityClassify commodityClassify) {
        return service.updateClassById(commodityClassify);
    }


    /**
     * 删除对应商品分类信息
     * @param id
     * @return
     */
//    @ApiOperation("删除对应商品分类信息")
    @DeleteMapping("/delete-Class-ById/{id}")
//    @ApiImplicitParam(value = "商品分类表主键",name = "id",required = true,paramType = "path",dataType = "int")
    public Result deleteClassById(@PathVariable("id")Integer id) {
        return service.deleteClassById(id);
    }


    /**
     * 批量删除对应商品分类信息
     * @param classIds
     * @return
     */
//    @ApiOperation("批量删除对应商品分类信息")
    @PostMapping("/delete-ClassBy-BatchId")
//    @ApiImplicitParam(value = "商品分类主键数组", name = "classIds",paramType = "body",required = true,dataType = "int")
    public Result deleteClassByBatchId(@RequestBody(required = true) Integer[] classIds) {
        return service.deleteClassByBatchId(classIds);
    }

}
