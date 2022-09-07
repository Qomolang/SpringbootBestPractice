package com.magnus.domain.org.model;

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
public class Org  {

    private Long id;

    private String name;

    private String fullName;

    private String corpId;

    private Long superAdminUid;

    private String ext;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;

    private Long createBy;

    private Long modifiedBy;

    private Long deleteTag;


}
