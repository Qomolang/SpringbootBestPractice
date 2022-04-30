package com.magnus.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 84028
 */
@RestController
public class ApiController {

    @GetMapping("/api")
    public String index() {
        return "guten tag";
    }

}