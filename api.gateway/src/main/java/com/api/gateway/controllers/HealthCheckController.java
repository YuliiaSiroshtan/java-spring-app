package com.api.gateway.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/health-check")
public class HealthCheckController {

    @GetMapping("/check")
    public Boolean checkGatewayStatus() {
        return true;
    }
}
