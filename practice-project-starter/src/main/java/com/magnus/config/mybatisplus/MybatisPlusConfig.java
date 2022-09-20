package com.magnus.config.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.magnus.infrastructure.common.enums.DBTimeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * @author gaosong
 */
@Slf4j
@Configuration
@MapperScan("com.magnus.infrastructure.dao.*.mapper")
public class MybatisPlusConfig implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, DBTimeEnum.CreateTime.getCode(), LocalDateTime.class, LocalDateTime.now());
        this.strictUpdateFill(metaObject, DBTimeEnum.UpdateTIme.getCode(), LocalDateTime.class, LocalDateTime.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, DBTimeEnum.UpdateTIme.getCode(), LocalDateTime.class, LocalDateTime.now());
    }
}