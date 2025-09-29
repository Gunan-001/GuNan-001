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

public class PersonalInformation {
    //@ApiModelProperty(value = "个人资料主键")
    private Integer id;

    @NotNull(message = "用户昵称不能为空")
    //@ApiModelProperty("用户昵称")
    private String nickName;

    @NotNull(message = "用户性别不能为空")
    //@ApiModelProperty("用户性别")
    private String userSex;

    @NotNull(message = "用户账号不能为空")
    //@ApiModelProperty("用户账号")
    private String userName;

    @NotNull(message = "用户密码不能为空")
    //@ApiModelProperty("用户密码")
    private String userPassword;


    @NotNull(message = "密保手机不能为空")
    //@ApiModelProperty("密保手机")
    private String userPhone;

    //@ApiModelProperty(value = "用户状态")
    @Range(min = 0,max = 1,message = "用户状态只能是0或1")
    private Integer userStatus;
}
