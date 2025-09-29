package com.small.fruitandvagetablemallspringboot.service;

import com.small.fruitandvagetablemallspringboot.pojo.PersonalInformation;
import com.small.fruitandvagetablemallspringboot.result.Result;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PersonInfoService {

    /**
     * 查询所有用户信息 返回用户信息集合
     * @return
     */
    Result queryForPersonalInformation();


    /**
     * 根据id查询对应用户信息
     * @return
     */
    Result queryForPersonalInformationById(@Param("id") Integer id);

    /**
     * 添加用户信息
     * @param
     * @return
     */
    Result addPersonalInfo(PersonalInformation personalInformation);

    /**
     * 更新用户信息
     * @param
     * @return
     */
    Result updatePersonalInfoById(List<PersonalInformation> personalInformation);

    /**
     * 删除用户信息
     * @param id
     * @return
     */
    Result deletePersonalInfoById(@Param("id") Integer id);

    /**
     * 根据账户密码查询用户信息
     * @return
     */
    Result queryForPersonByUserNameAndPassword(PersonalInformation person);

}
