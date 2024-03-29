package com.magnus.api.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author gaosong
 */
@Data
public class CommonRequest {
    @NotNull(message = "姓名不能为空")
    String name;
}
