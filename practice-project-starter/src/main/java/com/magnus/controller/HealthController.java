package com.magnus.controller;


import com.magnus.api.model.request.CommonRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author 84028
 */
@RestController
public class HealthController {

    @GetMapping("/health")
    public String health() {
        return "success";
    }

    @PostMapping("/test")
    public String test(@RequestBody @Valid CommonRequest request) {

        return "success";
    }

}
