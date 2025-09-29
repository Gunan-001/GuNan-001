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

public class CommodityClassify {

    private Integer id;

    @NotNull(message = "商品分类名称不能为空")

    private String commoditTypeName;
}
