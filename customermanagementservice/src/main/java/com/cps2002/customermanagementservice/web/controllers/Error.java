package com.cps2002.customermanagementservice.web.controllers;

import org.springframework.http.ResponseEntity;

public class Error {
    private String message;

    private Error(String message) {
        this.message = message;
    }

    public static ResponseEntity<Error> message(String message) {
        return ResponseEntity.badRequest().body(new Error(message));
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
