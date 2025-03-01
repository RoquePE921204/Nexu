package com.nexu.restapi.serviceImpl;

import com.nexu.restapi.custom.exceptions.GeneralControlledException;
import com.nexu.restapi.custom.mappers.ModelMapper;
import com.nexu.restapi.entities.Brand;
import com.nexu.restapi.entities.Model;
import com.nexu.restapi.repositories.ModelRepository;
import com.nexu.restapi.requests.UpdateModelRequest;
import com.nexu.restapi.responses.ModelResponse;
import com.nexu.restapi.services.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ModelServiceImpl implements ModelService {

    @Autowired
    private ModelRepository repository;

    @Override
    public ModelResponse updateModel(String id, UpdateModelRequest request) {
        Long modelId = this.stringToLong(id);
        if (modelId == null) {
            throw new GeneralControlledException("La id es invalido");
        }
        Optional<Model> optModel = this.repository.findById(modelId);
        if (optModel.isEmpty()) {
            throw new GeneralControlledException("El modelo a actualizar no existe");
        }
        Model model = optModel.get();
        model.setAveragePrice(request.getAveragePrice());
        model = this.repository.save(model);
        return ModelMapper.entityToResponse(model);
    }

    @Override
    public List<ModelResponse> getModels(String greater, String lower) {
        Long great = this.stringToLong(greater);
        Long low = this.stringToLong(lower);
        List<Model> models = null;
        if (great != null && low != null && great > 0 && low > 0) {
            models = this.repository.findByFilters(great, low);
        } else if (great != null && great > 0) {
            models = this.repository.findByGreater(great);
        } else if (low != null && low > 0) {
            models = this.repository.findByLower(low);
        } else {
            models = this.repository.findAll();
        }
        if (models == null || models.isEmpty()) {
            throw new GeneralControlledException("No se encontraron resultados para el id consultado");
        } else {
            return ModelMapper.entitiesToResponses(models);
        }
    }

    private Long stringToLong(String id) {
        try {
            return id != null ? Long.valueOf(id) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
