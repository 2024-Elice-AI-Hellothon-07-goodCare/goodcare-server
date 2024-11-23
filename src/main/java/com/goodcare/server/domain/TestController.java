package com.goodcare.server.domain;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "test", description = "react cors 확인용")
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
