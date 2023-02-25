package com.magnus.domain.dept.model;

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
 * @since 2023-02-25
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Dept  {

    private Long id;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;

    private Long createBy;

    private Long modifiedBy;

    private String name;

    private Long memberSize;

    private String orgId;

    private String ext;

    private Long deleteTag;


}
