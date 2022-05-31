package com.magnus.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
public class DemoCommand {

    @NotBlank(message = "姓名不为空")
    String name;


    @NotNull(message = "年龄不为空")
    Integer age;

}
