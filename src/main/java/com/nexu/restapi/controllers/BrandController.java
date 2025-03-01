package com.nexu.restapi.controllers;

import com.nexu.restapi.requests.NewBrandRequest;
import com.nexu.restapi.requests.NewModelRequest;
import com.nexu.restapi.responses.BrandResponse;
import com.nexu.restapi.responses.ModelResponse;
import com.nexu.restapi.services.BrandService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "brands")
@RestController
@Validated
public class BrandController {

    @Autowired
    private BrandService service;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BrandResponse> getBrands() {
        return this.service.getBrands();
    }

    @GetMapping(value = "/{id}/models", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ModelResponse> getModels(@PathVariable @Pattern(regexp = "[1-9]\\d*"
            , message = "El id de la marca debe ser mayor a 0") String id
        , @RequestParam(required = false) @Pattern(regexp = "[1-9]\\d*"
            , message = "El filtro de mayor que debe ser mayor un numero y mayor que 0") String greater
        , @RequestParam(required = false) @Pattern(regexp = "[1-9]\\d*"
            , message = "El filtro de menor que debe ser mayor un numero y mayor que 0") String lower) {
        return this.service.getModels(id, greater, lower);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public BrandResponse saveBrand(@Valid @RequestBody NewBrandRequest request) {
        return this.service.saveBrand(request);
    }

    @PostMapping(value = "/{id}/models", produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelResponse saveModel(@PathVariable @Pattern(regexp = "[1-9]\\d*"
            , message = "El id de la marca debe ser mayor a 0") String id
        , @Valid @RequestBody NewModelRequest request) {
        return this.service.saveModel(id, request);
    }
}
