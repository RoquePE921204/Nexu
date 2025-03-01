package com.nexu.restapi.services;

import com.nexu.restapi.requests.NewBrandRequest;
import com.nexu.restapi.requests.NewModelRequest;
import com.nexu.restapi.responses.BrandResponse;
import com.nexu.restapi.responses.ModelResponse;

import java.util.List;

public interface BrandService {

    List<BrandResponse> getBrands();

    List<ModelResponse> getModels(String id, String greater, String lower);

    BrandResponse saveBrand(NewBrandRequest request);

    ModelResponse saveModel(String id, NewModelRequest request);
}
