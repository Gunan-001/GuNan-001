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
public class ShoppingStar {
  //@ApiModelProperty(value = "收藏主键")
  private Integer id;

  @NotNull(message = "商品信息id不能为空")
  //@ApiModelProperty(value = "商品信息id")
  private Integer commodityId;

  @NotNull(message = "用户id不能为空")
  //@ApiModelProperty(value = "用户id")
  private Integer userId;
}
