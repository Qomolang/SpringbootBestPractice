package com.magnus.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 84028
 */
@Slf4j
@Service
public class DemoService {


    //    public String test(@Valid @NotNull DemoCommand command){
    public String test(DemoCommand command) {

        log.info("[DemoService test] command:{}", command);

        return "hi";
    }
}
