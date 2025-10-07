package com.small.fruitandvagetablemallspringboot.dao;

import com.small.fruitandvagetablemallspringboot.pojo.PersonalInformation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 个人信息mapper
 */
@Mapper
@Repository
public interface PersonalInformationMapper {
    /**
     * 根据账户密码查询用户信息
     * @return
     */
    PersonalInformation queryForPersonByUserNameAndPassword(@Param("userName") String userName,
                                                @Param("password") String password);

    /**
     * 查询所有用户信息 返回用户信息集合
     * @return
     */
    List<PersonalInformation> queryForPersonalInformation();


    /**
     * 根据id查询对应用户信息
     * @return
     */
    PersonalInformation queryForPersonalInformationById(@Param("id") Integer id);

    /**
     * 添加用户信息
     * @param
     * @return
     */
    Integer addPersonalInfo(PersonalInformation personalInformation);

    /**
     * 修改用户状态
     * @param
     * @return
     */
    Integer updatePersonalInfoById(PersonalInformation personalInformation);

    /**
     * 修改用户密码
     * @param
     * @return
     */
    Integer updatePasswordById(PersonalInformation personalInformation);

    /**
     * 删除用户信息
     * @param id
     * @return
     */
    Integer deletePersonalInfoById(@Param("id") Integer id);


    Integer updatePasswordByPhoneAndName(PersonalInformation personalInformation);
}
