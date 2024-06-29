package com.original.catalog.entities;

import java.io.Serializable;

import lombok.Getter;

// @AllArgsConstructor
@Getter
public class ApiResponse<T> implements Serializable{
    private static final long serialVersionUID = 1L;
    private String message;
    private T data;

    public ApiResponse(String message) {
        this.message = message;
    }

    public ApiResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }
}
