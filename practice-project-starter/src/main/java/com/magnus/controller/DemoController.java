package com.magnus.controller;

import com.magnus.api.model.request.CommonRequest;
import com.magnus.infrastructure.exception.catchlog.CatchAndLog;
import com.magnus.infrastructure.remote.http.DemoHttpServiceRemote;
import com.magnus.service.demo.DemoService;
import com.magnus.service.demo.command.DemoCommand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@CatchAndLog
@RestController
public class DemoController {

    @Resource
    private DemoService demoService;

    @GetMapping("/test")
    public String test() {
        return "success";
    }

    @GetMapping("/testerror")
    public String testError() {
        if (true) {
            throw new RuntimeException("这甚至不是错误");
        }
        return "success";
    }

    @PostMapping("/test1")
    public String test1(@RequestBody @Valid CommonRequest request) {
        if (true) {
            throw new RuntimeException("这甚至不是错误");
        }
        return "success";
    }

    @PostMapping("/test2")
    public String test2(@RequestBody @Valid CommonRequest request) {
        demoService.test(DemoCommand.builder().build());
        return "success";
    }

    @Resource
    DemoHttpServiceRemote demoHttpServiceRemote;

    @GetMapping("/retrofit/test")
    public String retrofitTest() {
        demoHttpServiceRemote.httpTest();
        return "success";
    }

}
