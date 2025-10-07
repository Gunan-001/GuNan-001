package com.small.fruitandvagetablemallspringboot.service.Impl;

import cn.hutool.Hutool;
import cn.hutool.crypto.digest.MD5;
import com.small.fruitandvagetablemallspringboot.dao.PersonalInformationMapper;
import com.small.fruitandvagetablemallspringboot.pojo.PersonalInformation;
import com.small.fruitandvagetablemallspringboot.result.Result;
import com.small.fruitandvagetablemallspringboot.result.ResultCodeEnum;
import com.small.fruitandvagetablemallspringboot.service.JwtService;
import com.small.fruitandvagetablemallspringboot.service.PersonInfoService;
import com.small.fruitandvagetablemallspringboot.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author GH
 * @date 2022-04-12 15:53
 */
@Service
public class PersonInfoServiceImpl implements PersonInfoService {
    //自动装配个人信息mapper
    @Autowired
    PersonalInformationMapper personInfoMapper;


    //自动装配token工具类
    @Autowired
    JwtService jwtService;

    /**
     * 查询所有个人信息
     *
     * @return
     */
    @Override
    public Result queryForPersonalInformation() {
        List<PersonalInformation> personalInformations = personInfoMapper.queryForPersonalInformation();
        for (PersonalInformation personalInformation : personalInformations) {
            personalInformation.setUserPassword("");
        }
        return Result.ok().data("personInfoList", personalInformations);
    }

    /**
     * 根据id查询对应个人信息
     *
     * @return
     */
    @Override
    public Result queryForPersonalInformationById(Integer id) {
        if (null == id) {
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        PersonalInformation personalInformation = personInfoMapper.queryForPersonalInformationById(id);
        personalInformation.setUserPassword("");
        return Result.ok().data("personInfo", personalInformation);
    }

    /**
     * 新增查个人信息
     *
     * @return
     */
    @Override
    public Result addPersonalInfo(PersonalInformation personInfo) {
        // 检查必填字段
        if (null == personInfo.getNickName() || "".equals(personInfo.getNickName().trim()) ||
                null == personInfo.getUserSex() || "".equals(personInfo.getUserSex().trim()) ||
                null == personInfo.getUserName() || "".equals(personInfo.getUserName().trim()) ||
                null == personInfo.getUserPassword() || "".equals(personInfo.getUserPassword().trim()) ||
                null == personInfo.getUserPhone() || "".equals(personInfo.getUserPhone().trim())
        ) {
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }

        personInfo.getUserName();
        // 密码加密处理
        personInfo.setUserPassword(MD5Utils.md5(personInfo.getUserPassword().trim()));

        // 保存用户信息
        Integer integer = personInfoMapper.addPersonalInfo(personInfo);

        if (integer < 1) {
            return Result.error(ResultCodeEnum.FAIL);
        }

        return Result.ok();
    }
    /**
     * 禁用或解除禁用用户
     *
     * @return
     */
    @Override
    public Result updatePersonalInfoById(List<PersonalInformation> personInfos) {

        if (null ==personInfos) {
            //必填参数为空
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        Integer integer=0;
        for (PersonalInformation personInfo : personInfos) {

            integer = personInfoMapper.updatePersonalInfoById(personInfo);
        }
        if (integer < 1) {
            return Result.error(ResultCodeEnum.FAIL);
        }
        return Result.ok();
    }

/**
 * 根据ID更新用户密码
 * @param personalInformation 包含用户ID和新密码的个人信息对象
 * @return 返回操作结果，成功返回ok，失败返回错误信息
 */
    @Override
    public Result updatePasswordById(PersonalInformation personalInformation) {
    // 检查ID是否为空，如果为空则返回参数错误
        if (null == personalInformation.getId()) {
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
    // 调用Mapper更新密码，获取受影响的行数
        personalInformation.setUserPassword(MD5Utils.md5(personalInformation.getUserPassword()));
        Integer integer = personInfoMapper.updatePasswordById(personalInformation);
    // 如果受影响行数小于1，说明更新失败，返回失败结果
        if (integer < 1) {
            return Result.error(ResultCodeEnum.FAIL);
        }
    // 更新成功，返回成功结果
        return Result.ok();
    }


    @Override
    public Result updatePasswordByPhoneAndName(PersonalInformation personalInformation) {
        if (null == personalInformation.getUserName()&&null == personalInformation.getUserPhone()&&null == personalInformation.getUserPassword()) {
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        // 调用Mapper更新密码，获取受影响的行数
        personalInformation.setUserPassword(MD5Utils.md5(personalInformation.getUserPassword()));
        Integer integer = personInfoMapper.updatePasswordByPhoneAndName(personalInformation);
        // 如果受影响行数小于1，说明更新失败，返回失败结果
        if (integer < 1) {
            return Result.error(ResultCodeEnum.FAIL);
        }
        // 更新成功，返回成功结果
        return Result.ok();
    }

    /**
     * 删除对应个人信息
     *
     * @return
     */
    @Override
    public Result deletePersonalInfoById(Integer id) {
        if (null == id) {
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        Integer integer = personInfoMapper.deletePersonalInfoById(id);
        if (integer < 1) {
            return Result.error(ResultCodeEnum.FAIL);
        }
        return Result.ok();
    }

    /**
     * 登录验证功能
     *
     *
     * @param person
     * @return
     */
    @Override
    public Result queryForPersonByUserNameAndPassword(PersonalInformation person) {
        person.setUserPassword( MD5Utils.md5(person.getUserPassword()));
        PersonalInformation personInfo = personInfoMapper.queryForPersonByUserNameAndPassword
                (person.getUserName(), person.getUserPassword());
        if (null == personInfo) {
            //账户或密码错误,登录失败
            return Result.error(ResultCodeEnum.LOGIN_ERROR);
        }
        if ( personInfo.getUserStatus()!=0) {
            //此用户已被禁用
            return Result.error(ResultCodeEnum.LOGIN_ERRORTWO);
        }
        //创建token
        String token = jwtService.createToken(person.getUserName());
        Map<String,Object> resultMap  = new HashMap<String,Object>();
        resultMap.put("userName",personInfo.getUserName());
        resultMap.put("userId",personInfo.getId());
        resultMap.put("nickName",personInfo.getNickName());
        resultMap.put("userSex",personInfo.getUserSex());
        resultMap.put("userPhone",personInfo.getUserPhone());
        resultMap.put("userStatus",personInfo.getUserStatus());
        resultMap.put("token",token);
        return Result.ok().data(resultMap);
    }
}
