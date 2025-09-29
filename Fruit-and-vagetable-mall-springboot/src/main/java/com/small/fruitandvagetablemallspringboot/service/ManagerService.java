package com.small.fruitandvagetablemallspringboot.service;

import com.small.fruitandvagetablemallspringboot.pojo.Manager;
import com.small.fruitandvagetablemallspringboot.result.Result;
import org.apache.ibatis.annotations.Param;

public interface ManagerService {
    /**
     * 查询所有管理员信息 返回管理员信息集合
     * @return
     */
    Result queryForManager();


    /**
     * 根据id查询对应管理员信息
     * @return
     */
    Result queryForManagerById(@Param("id") Integer id);


    /**
     * 根据账户密码查询用户信息
     * @return
     */
    Result queryForManagerByManaUserAndPwd(Manager manager);

    /**
     * 添加管理员信息
     * @param
     * @return
     */
    Result addManagerInfo(Manager manager);

    /**
     * 更新管理员信息
     * @param
     * @return
     */
    Result updateManagerInfoById(Manager manager);

    /**
     * 删除管理员信息
     * @param id
     * @return
     */
    Result deleteManagerInfoById(@Param("id") Integer id);

}
