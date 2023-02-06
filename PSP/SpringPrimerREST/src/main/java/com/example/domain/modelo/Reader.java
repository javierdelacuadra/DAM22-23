package com.example.domain.modelo;

import java.time.LocalDate;

public record Reader(int id, String name, LocalDate dateOfBirth) {
}