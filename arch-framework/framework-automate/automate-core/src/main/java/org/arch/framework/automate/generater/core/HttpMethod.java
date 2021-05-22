package org.arch.framework.automate.generater.core;

import lombok.Getter;

public enum HttpMethod {
    POST("@PostMapping"),
    PUT("@PutMapping"),
    DELETE("@DeleteMapping"),
    GET("@GetMapping"),
    ;

    @Getter
    private String method;

    HttpMethod(String method) {
        this.method = method;
    }
}
