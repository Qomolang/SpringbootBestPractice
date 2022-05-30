package com.magnus.api.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 84028
 */
@Data
public class CommonRequest {
    @NotNull(message = "姓名不能为空")
    String name;
}
