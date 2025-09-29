package com.small.fruitandvagetablemallspringboot.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated //数据校验

public class ShoppingAddress {
  //@ApiModelProperty(value = "收货地址主键")
  private Integer id;

  @NotNull(message = "个人资料主键不能为空")
  //@ApiModelProperty(value = "个人资料主键")
  private Integer userId;

  @NotNull(message = "收货人姓名不能为空")
  //@ApiModelProperty("收货人姓名")
  private String shoppingName;

  @NotNull(message = "收货人手机号码不能为空")
  //@ApiModelProperty("收货人手机号码")
  private String shoppingPhone;

  @NotNull(message = "收货地址不能为空")
  //@ApiModelProperty("收货地址")
  private String shoppingAddress;

  //@ApiModelProperty(value = "是否为默认地址")
  private Integer shoppingDefault;


}
