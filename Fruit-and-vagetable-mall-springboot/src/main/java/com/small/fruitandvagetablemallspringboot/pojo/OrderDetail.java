package com.small.fruitandvagetablemallspringboot.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated //数据校验

public class OrderDetail {

  //@ApiModelProperty(value = "订单信息主键")
  private Integer id;

  @NotNull(message = "收货地址id不能为空")
  //@ApiModelProperty(value = "收货地址id")
  private Integer addressId;

  @NotNull(message = "个人资料主键不能为空")
  //@ApiModelProperty(value = "个人资料主键")
  private Integer userId;

  @NotNull(message = "支付方式不能为空")
  //@ApiModelProperty("支付方式")
  private String orderPay;

  @NotNull(message = "订单金额不能为空")
  //@ApiModelProperty("订单金额")
  private Double commodityPrice;

  //@ApiModelProperty(value = "订单状态")
  @Range(min = 0,max = 2,message = "订单状态只能为0,1,2;0为待付款，1为待收货，2为待评价")
  private Integer orderStatus;

  //@ApiModelProperty("订单编号")
  private String orderNumber;

  @NotNull(message = "Json信息包含商品名称数量不能为空")
  //@ApiModelProperty("Json信息包含商品名称数量")
  private Object orderDetail;

  //@ApiModelProperty("订单生成时间")
  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8") //指定日期格式
  private Date orderTime;
}
