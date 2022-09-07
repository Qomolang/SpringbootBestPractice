package com.magnus.domain.employee.model;

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
public class Employee  {

    private Long id;

    private Long userId;

    private Long tenantId;

    private String nickName;

    private String empCode;

    private Long deleteTag;


}
