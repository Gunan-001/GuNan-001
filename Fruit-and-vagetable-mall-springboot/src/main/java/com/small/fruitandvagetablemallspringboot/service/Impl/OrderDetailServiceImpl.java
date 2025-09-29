package com.small.fruitandvagetablemallspringboot.service.Impl;

import com.alibaba.fastjson.JSON;
import com.small.fruitandvagetablemallspringboot.dao.OrderDetailMapper;
import com.small.fruitandvagetablemallspringboot.dao.ShoppingTrolleyMapper;
import com.small.fruitandvagetablemallspringboot.pojo.OrderDetail;
import com.small.fruitandvagetablemallspringboot.result.Result;
import com.small.fruitandvagetablemallspringboot.result.ResultCodeEnum;
import com.small.fruitandvagetablemallspringboot.service.OrderDetailService;
import com.small.fruitandvagetablemallspringboot.util.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author GH
 * @date 2022-04-12 15:53
 */
@Service
//事务控制
@Transactional
public class OrderDetailServiceImpl implements OrderDetailService {
    //自动装配订单mapper
    @Autowired
    OrderDetailMapper orderDetailMapper;

    @Autowired
    ShoppingTrolleyMapper trolleyMapper;

    //自动装配生成随机字符串的工具类
    @Autowired
    RandomString randomString;


    @Override
    public OrderDetail queryForOrderDetailById(Long id) {
        Integer ids = Integer.parseInt(id.toString());
        return orderDetailMapper.selectOrderDetailById(ids);
    }

    @Override
    public OrderDetail queryForOrderDetailInfoByOrderNumber(String orderNumber) {

        return orderDetailMapper.selectOrderDetailByOrderNumber(orderNumber);
    }

    @Override
    public int updateOrderDetail(OrderDetail orders) {
        return orderDetailMapper.updateOrderDetailInfoById(orders);
    }

    /**
     * 查询所有订单信息
     *
     * @return
     */
    @Override
    public Result queryForOrderDetailInfoAndPersonInfo() {
        List<Map<String, Object>> maps = orderDetailMapper.queryForOrderDetailInfoAndPersonInfo();
//        for (Map<String, Object> map : maps) {
//            Object object = map.get("orderDetail");
//            JSONObject jsonObject = JSON.parseObject((String) object);
//            map.put("orderDetail",jsonObject);
//        }
        return Result.ok().data("orderList", maps);
    }

    /**
     * 根据订单id查询订单信息
     *
     * @param id
     * @return
     */
    @Override
    public Result queryForOrderDetailInformationByOrderId(Integer id) {
        if (null == id) {
            //uid不能为空
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        List<Map<String, Object>> maps = orderDetailMapper.queryForOrderDetailInformationByOrderId(id);
        return Result.ok().data("orderList", maps);
    }

    /**
     * 根据用户id查询对应订单信息
     *
     * @return
     */
    @Override
    public Result queryForOrderDetailInformationById(Integer uid) {
        if (null == uid) {
            //uid不能为空
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        List<Map<String, Object>> maps = orderDetailMapper.queryForOrderDetailInformationById(uid);
        return Result.ok().data("orderList", maps);
    }

    /**
     * 添加一条订单信息
     *
     * @return
     */
    @Override
    public Result addOrderDetailInfo(OrderDetail orderDetail) {
        if (null == orderDetail.getAddressId() || null == orderDetail.getUserId() ||
                null == orderDetail.getOrderPay() || "".equals(orderDetail.getOrderPay().trim()) ||
                null == orderDetail.getCommodityPrice()
        ) {
            //必要参数有空值
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        trolleyMapper.deleteShoppingTrolleyByUserId(orderDetail.getUserId());
        String s = JSON.toJSONString(orderDetail.getOrderDetail());

        orderDetail.setOrderDetail(s);
        //生成随机字符串作为订单编号
        String randomString = this.randomString.getRandomString();
        orderDetail.setOrderNumber(randomString);

        Integer integer = orderDetailMapper.addOrderDetailInfo(orderDetail);
        if (integer < 1) {
            return Result.error(ResultCodeEnum.FAIL);
        }
        return Result.ok().data("orderId", orderDetail.getId());
    }

    /**
     * 修改对应订单信息
     *
     * @return
     */
    @Override
    public Result updateOrderDetailInfoById(List<OrderDetail> orderDetails) {
        if (null == orderDetails) {
            //必要参数有空值
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        Integer integer=0;
        for (OrderDetail orderDetail : orderDetails) {
            //System.out.println("orderDetail=====>"+orderDetail);
            integer = orderDetailMapper.updateOrderDetailInfoById(orderDetail);
        }
        if (integer < 1) {
            return Result.error(ResultCodeEnum.FAIL);
        }
        return Result.ok();
    }


    /**
     * 删除对应订单信息
     *
     * @return
     */
    @Override
    public Result deleteOrderDetailInfoById(Integer id) {
        if (null == id) {
            //主鍵不能为空
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        Integer integer = orderDetailMapper.deleteOrderDetailInfoById(id);
        if (integer < 1) {
            return Result.error(ResultCodeEnum.FAIL);
        }
        return Result.ok();
    }
}
