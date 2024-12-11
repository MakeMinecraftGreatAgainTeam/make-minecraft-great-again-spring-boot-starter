package org.mmga.spring.boot.starter.controller;

import org.mmga.spring.boot.starter.annotation.AuthMapping;
import org.mmga.spring.boot.starter.entities.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/")
    @AuthMapping
    public Result<Integer> test() {
        return Result.success(1);
    }
}
