package com.wuzx.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "mp_user")
public class User implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Integer age;

    private String name; // 查询不返回这个字段的值    @TableField(select = false)
    private String email;


    @TableField(exist = false)  // 查询不返回这个字段的值
    private String address;
}