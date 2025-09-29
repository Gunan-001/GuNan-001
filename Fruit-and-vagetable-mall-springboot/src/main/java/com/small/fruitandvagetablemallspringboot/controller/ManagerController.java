package com.small.fruitandvagetablemallspringboot.controller;

import com.small.fruitandvagetablemallspringboot.pojo.Manager;
import com.small.fruitandvagetablemallspringboot.result.Result;
import com.small.fruitandvagetablemallspringboot.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 管理员controller
 * @author GH
 * @date 2022-04-13 19:47
 */
@RestController
@RequestMapping("/managerInfo")
public class ManagerController {
    //自动装配管理员service
    @Autowired
    ManagerService service;

    /**
     * 查询所有管理员信息
     *
     * @return Result
     */
//    @ApiOperation("查询所有管理员信息")
    @GetMapping("/query-ForManager")
    public Result queryForManager() {
        return service.queryForManager();
    }

    /**
     * 根据id查询对应管理员信息
     *
     * @param id
     * @return Result
     */
//    @ApiOperation("根据ID查询管理员信息")
    @GetMapping("/query-ForManager-ById/{id}")
//    @ApiImplicitParam(value = "管理员信息表主键",name = "id",required = true,paramType = "path",dataType = "int")
    public Result queryForManagerById(@PathVariable("id") Integer id) {
        return service.queryForManagerById(id);
    }

    /**
     * 添加一条管理员信息
     * @param manager
     * @return Result
     */
//    @ApiOperation("添加一条管理员信息")
    @PostMapping("/add-ManagerInfo")
//    @ApiImplicitParam(value = "管理员信息实体", name = "Manager", paramType = "body",required = true,dataType = "manager")
    public Result addManagerInfo(@RequestBody(required = true) Manager manager) {

        return service.addManagerInfo(manager);
    }

    /**
     * 修改对应管理员信息
     * @param manager
     * @return Result
     */
//    @ApiOperation("修改对应管理员信息")
    @PutMapping("/update-ManagerInfo-ById")
//    @ApiImplicitParam(value = "管理员信息实体", name = "Manager", paramType = "body",required = true,dataType = "manager")
    public Result updateManagerInfoById(@RequestBody(required = true) Manager manager) {
        return service.updateManagerInfoById(manager);
    }


    /**
     * 删除对应管理员信息
     * @param id
     * @return
     */
//    @ApiOperation("删除对应管理员信息")
    @DeleteMapping("/delete-ManagerInfo-ById/{id}")
//    @ApiImplicitParam(value = "管理员信息表主键",name = "id",required = true,paramType = "path",dataType = "int")
    public Result deleteManagerInfoById(@PathVariable("id")Integer id) {
        return service.deleteManagerInfoById(id);
    }


    /**
     * 用户登录校验
     *
     * @param manager
     * @return
     */
//    @ApiOperation("管理员登录校验")
    @PostMapping("/login")

    public Result queryForManagerByManaUserAndPwd(@RequestBody(required = true) Manager manager) {
        Result result = service.queryForManagerByManaUserAndPwd(manager);
        return result;
    }
}
