package com.magnus.domain.user.model;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User  {

    private Long id;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 修改人
     */
    private Long modifiedBy;

    private String name;

    private String mobile;

    private String tag;

    private String ext;

    /**
     * 是否删除
     */
    private Long deleteTag;


}
