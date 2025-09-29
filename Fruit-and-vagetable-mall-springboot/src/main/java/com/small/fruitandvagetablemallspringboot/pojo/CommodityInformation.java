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

public class CommodityInformation {

    private Integer id;


    private String commodityInformation;

    //@ApiModelProperty(value = "商品名字")
    @NotNull(message = "商品名字不能为空")
    private String commodityName;


    @Range(max = 1,min = 0,message = "是否为热门推荐必须为0或1")
    //@ApiModelProperty(value = "是否为热门推荐")
    private Integer commodityHot;

    //@ApiModelProperty("运费")
    private Double commodityFreight;

    //@ApiModelProperty("商品价格")
    @NotNull(message = "商品价格不能为空")
    private Double commodityPrice;

    //@ApiModelProperty("商品图片")
    private String commodityImage;

    //@ApiModelProperty(value = "库存数量")
    @NotNull(message = "库存数量不能为空")
    private Integer commodityNumber;

    //@ApiModelProperty(value = "商品分类id",example = "1")
    @NotNull(message = "商品分类id不能为空")
    private Integer commodityClassId;


}
