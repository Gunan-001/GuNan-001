package com.small.fruitandvagetablemallspringboot.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated //数据校验
//@ApiModel(value = "slideshow",description ="轮播图实体")
public class Slideshow {
  //@ApiModelProperty(value = "轮播图表主键")
  private Integer id;

  @NotNull(message = "轮播图路径不能为空")
  //@ApiModelProperty("轮播图路径")
  private String path;

  @NotNull(message = "轮播图名称不能为空")
  //@ApiModelProperty("轮播图名称")
  private String name;


  @Range(min = 0,max = 1,message = "是否显示的值必须为0或1")
  //@ApiModelProperty(value = "是否显示0为不显示,1为显示")
  private Integer status;

}
