package com.magnus.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@MapperScan("com.magnus.infrastructure.dao.*.mapper")
public class MybatisPlusConfig {

}
