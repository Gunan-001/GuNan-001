package com.small.fruitandvagetablemallspringboot.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated //数据校验
public class MessageBoard {
  //@ApiModelProperty(value = "留言板主键")
  private Integer id;

  @NotNull(message = "用户id不能为空")
  //@ApiModelProperty(value = "用户id")
  private Integer userId;

  @NotNull(message = "留言信息不能为空")
  //@ApiModelProperty("留言信息")
  private String messageInfo;

  //@ApiModelProperty("商家回复")
  private String shopBack;

  //@ApiModelProperty("留言时间")
  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8") //指定日期格式
  private Date messageTime;

  //@ApiModelProperty("商家回复时间")
  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8") //指定日期格式
  private Date backTime;
}
