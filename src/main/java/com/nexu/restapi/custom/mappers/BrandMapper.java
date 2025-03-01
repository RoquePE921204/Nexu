package com.nexu.restapi.custom.mappers;

import com.nexu.restapi.entities.Brand;
import com.nexu.restapi.requests.NewBrandRequest;
import com.nexu.restapi.responses.BrandResponse;

import java.util.List;
import java.util.stream.Collectors;

public class BrandMapper {

    public static Brand requestToEntity(NewBrandRequest request) {
        if (request == null) {
            return null;
        }
        Brand brand = new Brand();
        brand.setNombre(request.getNombre());
        return brand;
    }

    public static BrandResponse entityToResponse(Brand brand) {
        if (brand == null) {
            return null;
        }

        BrandResponse response = new BrandResponse();
        response.setId(brand.getId());
        response.setNombre(brand.getNombre());
        response.setAveragePrice(brand.getAveragePrice());
        return response;
    }

    public static List<BrandResponse> entitiesToResponses(List<Brand> brands) {
        if (brands == null) {
            return null;
        }

        return brands.stream().map(BrandMapper::entityToResponse).collect(Collectors.toList());
    }
}
