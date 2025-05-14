package com.example.login.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class SwaggerTestController {

    @Operation(summary = "Test endpoint", description = "Simple test endpoint to verify Swagger documentation")
    @ApiResponse(responseCode = "200", description = "Success")
    @GetMapping
    public String test() {
        return "Swagger test endpoint works!";
    }
}