package com.nexu.restapi.custom.mappers;

import com.nexu.restapi.entities.Model;
import com.nexu.restapi.requests.NewModelRequest;
import com.nexu.restapi.responses.ModelResponse;

import java.util.List;
import java.util.stream.Collectors;

public class ModelMapper {

    public static Model requestToEntity(NewModelRequest request) {
        if (request == null) {
            return null;
        }
        Model model = new Model();
        model.setName(request.getName());
        model.setAveragePrice(request.getAveragePrice());
        return model;
    }

    public static ModelResponse entityToResponse(Model model) {
        if (model == null) {
            return null;
        }

        ModelResponse response = new ModelResponse();
        response.setId(model.getId());
        response.setName(model.getName());
        response.setAveragePrice(model.getAveragePrice());
        return response;
    }

    public static List<ModelResponse> entitiesToResponses(List<Model> models) {
        if (models == null) {
            return null;
        }

        return models.stream().map(ModelMapper::entityToResponse).collect(Collectors.toList());
    }
}
