package com.magnus.controller;

import com.magnus.api.model.request.CommonRequest;
import com.magnus.service.DemoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
public class DemoController {

    @Resource
    private DemoService demoService;

    @PostMapping("/test")
    public String test(@RequestBody @Valid CommonRequest request) {
        if (true) {
            throw new RuntimeException("这甚至不是错误");
        }
        return "success";
    }

    @PostMapping("/test2")
    public String test2(@RequestBody @Valid CommonRequest request) {
        demoService.test(null);
        return "success";
    }

}
