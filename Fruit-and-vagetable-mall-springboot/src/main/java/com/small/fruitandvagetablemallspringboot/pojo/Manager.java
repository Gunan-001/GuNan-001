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
public class Manager {
  //@ApiModelProperty(value = "管理员信息主键")
  private Integer id;

  @NotNull(message = "管理员名字不能为空")
  //@ApiModelProperty("管理员名字")
  private String name;

  @NotNull(message = "管理员账号不能为空")
  //@ApiModelProperty("管理员账号")
  private String account;

  @NotNull(message = "管理员密码不能为空")
  //@ApiModelProperty("管理员密码")
  private String password;
}
