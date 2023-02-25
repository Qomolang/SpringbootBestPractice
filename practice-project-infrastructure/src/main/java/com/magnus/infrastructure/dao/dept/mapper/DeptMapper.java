package com.magnus.infrastructure.dao.dept.mapper;

import com.magnus.infrastructure.dao.dept.model.DeptDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author gs
 * @since 2023-02-25
 */
public interface DeptMapper extends BaseMapper<DeptDO> {

    Long addMemberSize(@Param("id") Long id);

}
