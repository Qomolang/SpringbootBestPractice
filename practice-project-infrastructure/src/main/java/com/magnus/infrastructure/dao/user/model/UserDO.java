package com.magnus.infrastructure.dao.user.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author gs
 * @since 2024-05-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user")
public class UserDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建时间
     */
    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "gmt_modified", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;

    /**
     * 创建人
     */
    @TableField("create_by")
    private Long createBy;

    /**
     * 修改人
     */
    @TableField("modified_by")
    private Long modifiedBy;

    @TableField("name")
    private String name;

    @TableField("mobile")
    private String mobile;

    @TableField("tag")
    private String tag;

    @TableField("ext")
    private String ext;

    /**
     * 是否删除
     */
    @TableField("is_deleted")
    private Long deleteTag;


}
