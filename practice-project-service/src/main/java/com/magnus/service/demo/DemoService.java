package com.magnus.service.demo;

import com.magnus.service.demo.command.DemoCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author gaosong
 */
@Validated
@Slf4j
@Service
public class DemoService {

    public String test(@Valid @NotNull DemoCommand command) {

        log.info("[DemoService test] command:{}", command);

        return "hi";
    }
}
