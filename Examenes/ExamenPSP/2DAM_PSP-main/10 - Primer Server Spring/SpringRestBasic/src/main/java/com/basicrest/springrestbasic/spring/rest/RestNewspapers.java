package com.basicrest.springrestbasic.spring.rest;

import com.basicrest.springrestbasic.domain.modelo.Newspaper;
import com.basicrest.springrestbasic.domain.servicios.NewspaperServices;
import com.basicrest.springrestbasic.spring.common.RestConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RestConstants.API_NEWSPAPERS)
@Tag(name = RestConstants.NEWSPAPERS_API, description = RestConstants.NEWSPAPERS_ENDPOINT)
public class RestNewspapers {

    private final NewspaperServices newspaperServices;


    public RestNewspapers(NewspaperServices userServices) {
        this.newspaperServices = userServices;
    }
    @Operation(summary = RestConstants.GET_NEWSPAPER_BY_ID)
    @GetMapping(RestConstants.PATH_PARAM_ID)
    public Newspaper getNewsById(@PathVariable Long id) {
        return newspaperServices.getNewsById(id);
    }
    @Operation(summary = RestConstants.GET_ALL_NEWSPAPERS)
    @GetMapping
    public List<Newspaper> getAllUsers() {
        return newspaperServices.getAllNewspapers();
    }

    @Operation(summary = RestConstants.CREATE_A_NEWSPAPER)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Newspaper createNewspaper(@RequestBody Newspaper newspaper) {
        return newspaperServices.createNewspaper(newspaper);
    }

    @Operation(summary = RestConstants.UPDATE_A_NEWSPAPER)
    @PutMapping(RestConstants.PATH_PARAM_ID)
    public Newspaper updateNewspaper(@RequestBody Newspaper newspaper) {
        return newspaperServices.updateNewspaper(newspaper);
    }

    @Operation(summary = RestConstants.DELETE_A_NEWSPAPER)
    @DeleteMapping(RestConstants.PATH_PARAM_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        newspaperServices.deleteNewspaper(id);
    }
}
