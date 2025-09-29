package com.small.fruitandvagetablemallspringboot.dao;

import com.small.fruitandvagetablemallspringboot.pojo.Manager;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 管理员mapper
 */
@Mapper
@Repository
public interface ManagerMapper {
    /**
     * 查询所有管理员信息 返回管理员信息集合
     * @return
     */
    List<Manager> queryForManager();


    /**
     * 根据账号密码查询对应管理员信息 登录校验
     *
     */
    Manager queryForManagerByManaUserAndPwd(@Param("account") String account,
                                @Param("password") String password);

    /**
     * 根据id查询对应管理员信息
     * @return
     */
    Manager queryForManagerById(@Param("id") Integer id);

    /**
     * 添加管理员信息
     * @param 
     * @return
     */
    Integer addManagerInfo(Manager manager);

    /**
     * 更新管理员信息
     * @param 
     * @return
     */
    Integer updateManagerInfoById(Manager manager);

    /**
     * 删除管理员信息
     * @param id
     * @return
     */
    Integer deleteManagerInfoById(@Param("id") Integer id);
    
}
