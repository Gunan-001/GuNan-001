package com.small.fruitandvagetablemallspringboot.service;

import com.small.fruitandvagetablemallspringboot.pojo.OrderDetail;
import com.small.fruitandvagetablemallspringboot.result.Result;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单service接口
 */
public interface OrderDetailService {
    /**
     * 支付查询 根据订单id查询
     * @return
     */
    public OrderDetail queryForOrderDetailById(Long id);

    /**
     * 支付查询 根据订单号查询  orderNumber
     * @return
     */
    public OrderDetail queryForOrderDetailInfoByOrderNumber(String orderNumber);
    /**
     * 支付修改
     * @return
     */
    int updateOrderDetail(OrderDetail orders);


    /**
     * 关联查询 订单信息和订单分类
     * @return
     */
    Result queryForOrderDetailInfoAndPersonInfo();

    /**
     * 关联查询 根据订单id查询对应订单信息
     * @return
     */
    Result queryForOrderDetailInformationByOrderId(@Param("id") Integer id);

    /**
     * 关联查询 根据用户id查询对应订单信息
     * @return
     */
    Result queryForOrderDetailInformationById(@Param("uid") Integer uid);

    /**
     * 添加订单信息
     * @param
     * @return
     */
    Result addOrderDetailInfo(OrderDetail orderDetail);

    /**
     * 更新订单信息
     * @param
     * @return
     */
    Result updateOrderDetailInfoById(List<OrderDetail> orderDetail );

    /**
     * 根据订单id删除订单信息
     * @param id
     * @return
     */
    Result deleteOrderDetailInfoById(@Param("id") Integer id);
}
