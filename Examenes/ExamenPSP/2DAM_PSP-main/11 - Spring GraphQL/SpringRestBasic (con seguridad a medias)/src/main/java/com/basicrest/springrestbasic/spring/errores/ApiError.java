package com.basicrest.springrestbasic.spring.errores;


import java.time.LocalDateTime;

public record ApiError(String message, LocalDateTime fecha, int code) {
}
