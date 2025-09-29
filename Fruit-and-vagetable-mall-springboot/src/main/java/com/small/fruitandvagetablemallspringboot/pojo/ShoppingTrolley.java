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
//@ApiModel(value = "shoppingTrolley",description ="购物车实体")
public class ShoppingTrolley {
 // @ApiModelProperty(value = "购物车主键")
  private Integer id;

  @NotNull(message = "个人资料主键不能为空")
 // @ApiModelProperty(value = "个人资料主键")
  private Integer userId;

  @NotNull(message = "商品信息id不能为空")
 // @ApiModelProperty(value = "商品信息id")
  private Integer commodityId;

  @NotNull(message = "商品数量不能为空")
 // @ApiModelProperty(value = "商品数量")
  private Integer commodityNumber;


}
