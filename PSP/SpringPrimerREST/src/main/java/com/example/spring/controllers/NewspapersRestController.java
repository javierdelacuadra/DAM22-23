package com.example.spring.controllers;

import com.example.common.Constantes;
import com.example.domain.modelo.Newspaper;
import com.example.domain.services.ServiciosNewspapers;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constantes.API_NEWSPAPERS_PATH)
public class NewspapersRestController {

    private final ServiciosNewspapers servicios;

    public NewspapersRestController(ServiciosNewspapers servicios) {
        this.servicios = servicios;
    }

    @GetMapping
    public List<Newspaper> getAllNewspapers() {
        return servicios.getNewspapers();
    }

    @GetMapping(Constantes.ID_PATH)
    public Newspaper getNewspaperById(@PathVariable Long id) {
        return servicios.getNewspaperById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Newspaper createNewspaper(@RequestBody Newspaper newspaper) {
        return servicios.createNewspaper(newspaper);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public int updateNewspaper(@RequestBody Newspaper newspaper) {
        return servicios.updateNewspaper(newspaper);
    }

    @DeleteMapping(Constantes.ID_PATH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNewspaper(@PathVariable Long id) {
        servicios.deleteNewspaper(id);
    }
}
