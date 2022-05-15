package com.magnus.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 84028
 */
@RestController
public class HealthController {

    @GetMapping("/health")
    public String health() {
        return "success";
    }

}
