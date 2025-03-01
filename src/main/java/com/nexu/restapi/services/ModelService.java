package com.nexu.restapi.services;

import com.nexu.restapi.requests.UpdateModelRequest;
import com.nexu.restapi.responses.ModelResponse;

import java.util.List;

public interface ModelService {

    ModelResponse updateModel(String id, UpdateModelRequest request);

    List<ModelResponse> getModels(String greater, String lower);
}
