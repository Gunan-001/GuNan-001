package com.small.fruitandvagetablemallspringboot.dao;

import com.small.fruitandvagetablemallspringboot.pojo.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 订单mapper
 */
@Mapper
@Repository
public interface OrderDetailMapper {


    /**
     * 支付查询
     * 根据订单id查询
     * @return
     */
    OrderDetail selectOrderDetailById(Integer ids);

    /**
     * 支付查询
     * 根据订单号查询  orderNumber
     * @return
     */
    OrderDetail selectOrderDetailByOrderNumber(String orderNumber);

    /**
     * 查询所有订单编号信息
     * @return
     */
    List<String> queryAllDetailNum();

    /**
     * 关联查询 订单信息和订单分类
     * @return
     */
    List<Map<String,Object>> queryForOrderDetailInfoAndPersonInfo();


    /**
     * 关联查询 根据订单查询对应订单信息
     * @return
     */
    List<Map<String,Object>> queryForOrderDetailInformationByOrderId(@Param("id") Integer id);

    /**
     * 关联查询 根据用户id查询对应订单信息
     * @return
     */
    List<Map<String,Object>> queryForOrderDetailInformationById(@Param("uid") Integer uid);

    /**
     * 添加订单信息
     * @param 
     * @return
     */
    Integer addOrderDetailInfo(OrderDetail orderDetail);

    /**
     * 更新订单信息
     * @param 
     * @return
     */
    Integer updateOrderDetailInfoById(OrderDetail orderDetails );

    /**
     * 根据订单id删除订单信息
     * @param id
     * @return
     */
    Integer deleteOrderDetailInfoById(@Param("id") Integer id);

}
