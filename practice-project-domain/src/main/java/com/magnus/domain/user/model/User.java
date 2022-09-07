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
 * @since 2022-09-08
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User  {

    private Long id;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;

    private Long createBy;

    private Long modifiedBy;

    private String name;

    private String tag;

    private Long deleteTag;


}
