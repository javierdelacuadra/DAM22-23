package com.example.spring.controllers;

import com.example.domain.modelo.Reader;
import com.example.domain.services.ServiciosReaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/readers")
public class ReadersRestController {

    private final ServiciosReaders servicios;

    public ReadersRestController(ServiciosReaders servicios) {
        this.servicios = servicios;
    }

    @GetMapping
    public List<Reader> getAllReaders() {
        return servicios.getReaders();
    }

    @GetMapping("/{id}")
    public Reader getReaderById(@PathVariable Long id) {
        return servicios.getReaderById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reader createReader(@RequestBody Reader reader) {
        return servicios.createReader(reader);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public int updateReader(@RequestBody Reader reader) {
        return servicios.updateReader(reader);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReader(@PathVariable Long id) {
        servicios.deleteReader(id);
    }
}
