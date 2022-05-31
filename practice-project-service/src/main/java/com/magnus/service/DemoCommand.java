package com.magnus.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
public class DemoCommand {

    @NotNull(message = "姓名不为空")
    String name;

}
