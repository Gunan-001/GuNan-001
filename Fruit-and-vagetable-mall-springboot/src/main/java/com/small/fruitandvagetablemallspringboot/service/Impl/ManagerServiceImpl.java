package com.small.fruitandvagetablemallspringboot.service.Impl;

import com.small.fruitandvagetablemallspringboot.dao.ManagerMapper;
import com.small.fruitandvagetablemallspringboot.pojo.Manager;
import com.small.fruitandvagetablemallspringboot.result.Result;
import com.small.fruitandvagetablemallspringboot.result.ResultCodeEnum;
import com.small.fruitandvagetablemallspringboot.service.JwtService;
import com.small.fruitandvagetablemallspringboot.service.ManagerService;
import com.small.fruitandvagetablemallspringboot.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员信息表service
 * @author GH
 * @date 2022-04-12 15:52
 */
@Service
public class ManagerServiceImpl implements ManagerService {
    //自动装配 管理员mapper对象
    @Autowired
    ManagerMapper managerMapper;


    //自动装配token工具类
    @Autowired
    JwtService jwtService;

    /**
     * 查询所有管理员信息
     * @return Result
     */
    @Override
    public Result queryForManager() {
        List<Manager> managers = managerMapper.queryForManager();
        return Result.ok().data("managerList",managers);
    }

    /**
     * 根据id查询对应的管理员信息
     * @param id
     * @return Result
     */
    @Override
    public Result queryForManagerById(Integer id) {
        if(null==id){
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        Manager manager = managerMapper.queryForManagerById(id);
        return Result.ok().data("manager",manager);
    }

    /**
     * 登录验证功能
     *
     *
     * @param manager
     * @return
     */
    @Override
    public Result queryForManagerByManaUserAndPwd(Manager manager) {
        manager.setPassword(MD5Utils.md5(manager.getPassword()));
        Manager res = managerMapper.queryForManagerByManaUserAndPwd(manager.getAccount(), manager.getPassword());
        if (null == res) {
                //账户或密码错误,登录失败
                return Result.error(ResultCodeEnum.LOGIN_ERROR);
            }
            //创建token
            String token = jwtService.createToken(manager.getAccount());
            Map<String,Object> resultMap  = new HashMap<String,Object>();
            resultMap.put("account",res.getAccount());
            resultMap.put("token",token);
            return Result.ok().data(resultMap);
    }


    /**
     * 添加一条管理员信息
     * @param manager
     * @return Result
     */
    @Override
    public Result addManagerInfo(Manager manager) {
        if(null==manager.getName() || "".equals(manager.getName().trim())
                ||null==manager.getAccount() || "".equals(manager.getAccount().trim())
                ||null==manager.getPassword() ||"".equals(manager.getPassword().trim()))
        {
            //如果用户输入参数中有存在必填项为空则返回参数异常
            return  Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        Integer integer = managerMapper.addManagerInfo(manager);
        if(integer<1){
            return Result.error(ResultCodeEnum.FAIL);
        }
        return Result.ok();
    }

    /**
     * 修改对应管理员信息
     * @param manager
     * @return Result
     */
    @Override
    public Result updateManagerInfoById(Manager manager) {
        if(null==manager.getId())
        {
            //如果用户输入参数中有存在必填项为空则返回参数异常
            return  Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        Integer integer = managerMapper.updateManagerInfoById(manager);
        if(integer<1){
            return Result.error(ResultCodeEnum.FAIL);
        }
        return Result.ok();
    }

    /**
     * 删除对应管理员信息
     * @param id
     * @return Result
     */
    @Override
    public Result deleteManagerInfoById(Integer id) {
        if(null==id){
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        Integer integer = managerMapper.deleteManagerInfoById(id);
        if(integer<1){
            return Result.error(ResultCodeEnum.FAIL);
        }
        return Result.ok();
    }
}
