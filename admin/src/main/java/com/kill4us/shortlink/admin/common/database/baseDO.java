package com.kill4us.shortlink.admin.common.database;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

/**
 * 数据库持久层自动填充参数
 */
@Data
public class baseDO {

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 删除标识 0：未删除 1：已删除
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer delFlag;
}
