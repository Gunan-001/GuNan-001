package com.small.fruitandvagetablemallspringboot.controller;

import com.small.fruitandvagetablemallspringboot.pojo.PersonalInformation;
import com.small.fruitandvagetablemallspringboot.result.Result;
import com.small.fruitandvagetablemallspringboot.service.PersonInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户信息controller
 *
 * @author GH
 * @date 2022-04-14 17:15
 */
@RestController
@RequestMapping("/personInfo")

public class PersonInfoController {
    //自动装配用户service
    @Autowired
    PersonInfoService service;

    /**
     * 查询所有用户信息
     *
     * @return result
     */
    //@ApiOperation("查询所有用户信息")
    @GetMapping("/query-ForPersonalInformation")
    public Result queryForPersonalInformation() {
        return service.queryForPersonalInformation();
    }

    /**
     * 根据id查询对应用户信息
     *
     * @param id
     * @return
     */
    //@ApiOperation("根据ID查询用户信息")
    @GetMapping("/query-ForPersonalInformation-ById/{id}")
   // @ApiImplicitParam(value = "个人资料表主键", name = "id", required = true, paramType = "path", dataType = "int")
    public Result queryForOrderDetailInformationById(@PathVariable("id") Integer id) {
        return service.queryForPersonalInformationById(id);
    }

    /**
     * 添加一条用户信息
     *
     * @param personInfo
     * @return
     */
    //@ApiOperation("添加一条用户信息")
    @PostMapping("/add-PersonalInfo")
   // @ApiImplicitParam(value = "用户信息实体", name = "PersonalInformation", paramType = "body", required = true, dataType = "personInfo")
    public Result addPersonalInfo(@RequestBody(required = true) PersonalInformation personInfo) {
        return service.addPersonalInfo(personInfo);
    }

    /**
     * 修改对应用户信息
     *
     * @param personInfos
     * @return
     */
    //@ApiOperation("修改对应用户信息")
    @PutMapping("/update-PersonalInfo-ById")
   // @ApiImplicitParam(value = "用户信息实体", name = "PersonalInformation", paramType = "body", required = true, dataType = "personInfo")
    public Result updatePersonalInfoById(@RequestBody(required = false) List<PersonalInformation> personInfos) {
        return service.updatePersonalInfoById(personInfos);
    }


    /**
     * 删除对应用户信息
     *
     * @param id
     * @return
     */
    //@ApiOperation("删除对应用户信息")
    @DeleteMapping("/delete-PersonalInfo-ById/{id}")
   // @ApiImplicitParam(value = "个人资料主键", name = "id", required = true, paramType = "path", dataType = "int")
    public Result deletePersonalInfoById(@PathVariable("id") Integer id) {
        return service.deletePersonalInfoById(id);
    }


    /**
     * 用户登录校验
     *
     * @param person
     * @return
     */
    //@ApiOperation("用户登录校验")
    @PostMapping("/login")
    public Result queryForPersonByUserNameAndPassword(@RequestBody(required = true) PersonalInformation person) {
        System.out.println(person.getUserName() + "===>" + person);
        Result result = service.queryForPersonByUserNameAndPassword(person);
        return result;
    }
}
