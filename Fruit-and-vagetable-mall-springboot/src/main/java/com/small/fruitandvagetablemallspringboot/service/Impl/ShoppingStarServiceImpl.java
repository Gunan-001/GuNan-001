package com.small.fruitandvagetablemallspringboot.service.Impl;

import com.small.fruitandvagetablemallspringboot.dao.ShoppingStarMapper;
import com.small.fruitandvagetablemallspringboot.pojo.ShoppingStar;
import com.small.fruitandvagetablemallspringboot.result.Result;
import com.small.fruitandvagetablemallspringboot.result.ResultCodeEnum;
import com.small.fruitandvagetablemallspringboot.service.ShoppingStarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 收藏信息表service实现类
 *
 * @author GH
 * @date 2022-04-12 15:53
 */
@Service
public class ShoppingStarServiceImpl implements ShoppingStarService {
    @Autowired
    ShoppingStarMapper starMapper;


    /**
     * 查询所有收藏信息
     *
     * @return
     */
    @Override
    public Result queryForShoppingStarAndPersonInfo() {
        List<Map<String, Object>> maps = starMapper.queryForShoppingStarAndPersonInfo();

        return Result.ok().data("starList", maps);
    }

    /**
     * 根据用户id查询用户的收藏信息
     *
     * @return
     */
    @Override
    public Result queryForShoppingStarById(Integer uid) {
        List<Map<String, Object>> maps = starMapper.queryForShoppingStarById(uid);
        return Result.ok().data("starList", maps);
    }


    /**
     * 添加一条收藏信息收藏信息
     *
     * @return
     */
    @Override
    public Result addShoppingStar(ShoppingStar shoppingStar) {
        if (null == shoppingStar.getCommodityId() || null == shoppingStar.getUserId()) {
            //必填参数为空
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        Integer integer = starMapper.addShoppingStar(shoppingStar);
        if (integer < 1) {
            return Result.error(ResultCodeEnum.FAIL);
        }
        return Result.ok();
    }

    /**
     * 修改一条收藏信息收藏信息
     *
     * @return
     */
    @Override
    public Result updateShoppingStarById(ShoppingStar shoppingStar) {
        if (null == shoppingStar.getCommodityId() || null == shoppingStar.getUserId()
                || null == shoppingStar.getId()
        ) {
            //必填参数为空
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        Integer integer = starMapper.updateShoppingStarById(shoppingStar);
        if (integer < 1) {
            return Result.error(ResultCodeEnum.FAIL);
        }
        return Result.ok();
    }

    /**
     * 删除一条收藏信息
     *
     * @return
     */
    @Override
    public Result deleteShoppingStarById(Integer id) {
        if (null == id) {
            return Result.error(ResultCodeEnum.FAIL);
        }
        Integer integer = starMapper.deleteShoppingStarById(id);
        if (integer < 1) {
            return Result.error(ResultCodeEnum.FAIL);
        }
        return Result.ok();
    }
}
