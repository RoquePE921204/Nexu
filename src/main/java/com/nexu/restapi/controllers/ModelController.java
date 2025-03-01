package com.nexu.restapi.controllers;

import com.nexu.restapi.requests.UpdateModelRequest;
import com.nexu.restapi.responses.ModelResponse;
import com.nexu.restapi.services.ModelService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "models")
@RestController
@Validated
public class ModelController {

    @Autowired
    private ModelService service;

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelResponse updateModel( @PathVariable @Pattern(regexp = "[1-9]\\d*"
            , message = "El id del modelo debe ser mayor a 0") String id
        , @Valid @RequestBody UpdateModelRequest request) {
        return this.service.updateModel(id, request);
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ModelResponse> getModels(@RequestParam(required = false) @Pattern(regexp = "[1-9]\\d*"
                    , message = "El filtro de mayor que debe ser mayor un numero y mayor que 0") String greater
            , @RequestParam(required = false) @Pattern(regexp = "[1-9]\\d*"
                    , message = "El filtro de menor que debe ser mayor un numero y mayor que 0") String lower) {
        return this.service.getModels(greater, lower);
    }

}
