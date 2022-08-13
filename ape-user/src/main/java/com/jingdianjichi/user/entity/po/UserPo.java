package com.jingdianjichi.user.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import com.jingdianjichi.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

@TableName("user")
@Data
public class UserPo extends BaseEntity {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private String name;

    private Integer age;

}
