package com.small.fruitandvagetablemallspringboot.service.Impl;

import com.small.fruitandvagetablemallspringboot.dao.ShoppingAddressMapper;
import com.small.fruitandvagetablemallspringboot.pojo.ShoppingAddress;
import com.small.fruitandvagetablemallspringboot.result.Result;
import com.small.fruitandvagetablemallspringboot.result.ResultCodeEnum;
import com.small.fruitandvagetablemallspringboot.service.ShoppingAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 收货地址service实现
 *
 * @author GH
 * @date 2022-04-12 15:53
 */
@Service
public class ShoppingAddressServiceImpl implements ShoppingAddressService {
    //自动装配收货信息mapper
    @Autowired
    ShoppingAddressMapper addressMapper;

    /**
     * 查询所有收货信息
     *
     * @return
     */
    @Override
    public Result queryForShoppingAddressAndPersonInfo() {
        List<Map<String, Object>> maps = addressMapper.queryForShoppingAddressAndPersonInfo();
        return Result.ok().data("addressList", maps);
    }

    /**
     * 根据用户id查询对应收货信息
     *
     * @return
     */
    @Override
    public Result queryForShoppingAddressById(Integer uid) {
        List<Map<String, Object>> maps = addressMapper.queryForShoppingAddressById(uid);
        return Result.ok().data("addressList", maps);
    }

    /**
     * 添加一条收货信息
     *
     * @return
     */
    @Override
    public Result addShoppingAddress(ShoppingAddress shoppingAddress) {
        if (null == shoppingAddress.getUserId() ||
                null == shoppingAddress.getShoppingName() || "".equals(shoppingAddress.getShoppingName().trim())
                || null == shoppingAddress.getShoppingPhone() || "".equals(shoppingAddress.getShoppingPhone().trim())
                || null == shoppingAddress.getShoppingAddress() || "".equals(shoppingAddress.getShoppingAddress().trim())
        ) {
            //必要参数为空
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        Integer integer = addressMapper.addShoppingAddress(shoppingAddress);
        if (null == integer) {
            //查询异常
            return Result.error(ResultCodeEnum.FAIL);
        }
        return Result.ok();
    }

    /**
     * 修改对应收货信息
     *
     * @return
     */
    @Override
    public Result updateShoppingAddressById(ShoppingAddress shoppingAddress) {
        if (null == shoppingAddress.getUserId() ||
                null == shoppingAddress.getShoppingAddress() || "".equals(shoppingAddress.getShoppingAddress().trim())) {
            //必要参数为空
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }

        if(1==shoppingAddress.getShoppingDefault()){
            addressMapper.updateShoppingAddressByUserId(shoppingAddress.getUserId());
        }

        Integer integer = addressMapper.updateShoppingAddressById(shoppingAddress);
        if (null == integer) {
            //查询异常
            return Result.error(ResultCodeEnum.FAIL);
        }
        return Result.ok();
    }

    /**
     * 删除对应收货信息
     *
     * @return
     */
    @Override
    public Result deleteShoppingAddressById(Integer id) {
        if (null == id) {
            //id参数为空 返回参数异常
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        Integer integer = addressMapper.deleteShoppingAddressById(id);
        if (null == integer) {
            //查询异常
            return Result.error(ResultCodeEnum.FAIL);
        }
        return Result.ok();
    }
}
