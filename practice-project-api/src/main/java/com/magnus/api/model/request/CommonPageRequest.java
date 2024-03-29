package com.magnus.api.model.request;

import com.alibaba.cola.dto.PageQuery;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author gaosong
 */
@Data
public class CommonPageRequest extends PageQuery {
    @NotNull(message = "姓名不能为空")
    String name;
}
