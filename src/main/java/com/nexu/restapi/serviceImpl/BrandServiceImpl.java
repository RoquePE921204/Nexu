package com.nexu.restapi.serviceImpl;

import com.nexu.restapi.custom.exceptions.GeneralControlledException;
import com.nexu.restapi.custom.mappers.BrandMapper;
import com.nexu.restapi.custom.mappers.ModelMapper;
import com.nexu.restapi.entities.Brand;
import com.nexu.restapi.entities.Model;
import com.nexu.restapi.repositories.BrandRepository;
import com.nexu.restapi.repositories.ModelRepository;
import com.nexu.restapi.requests.NewBrandRequest;
import com.nexu.restapi.requests.NewModelRequest;
import com.nexu.restapi.responses.BrandResponse;
import com.nexu.restapi.responses.ModelResponse;
import com.nexu.restapi.services.BrandService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository repository;
    @Autowired
    private ModelRepository modelRepository;

    @Override
    public List<BrandResponse> getBrands() {
        List<Brand> brands = this.repository.findAllBrandsWithAveragePrice();
        return BrandMapper.entitiesToResponses(brands);
    }

    @Override
    public List<ModelResponse> getModels(String id, String greater, String lower) {
        Long great = this.stringToLong(greater);
        Long low = this.stringToLong(lower);
        Long brandId = this.stringToLong(id);
        Optional<Brand> optBrand = this.repository.findById(brandId);
        if (optBrand.isEmpty()) {
            throw new GeneralControlledException("La marca consultada no existe");
        }
        List<Model> models = null;
        if (great != null && low != null && great > 0 && low > 0) {
            models = this.modelRepository.findByBrandIdAndFilters(brandId, great, low);
        } else if (great != null && great > 0) {
            models = this.modelRepository.findByBrandIdAndGreater(brandId, great);
        } else if (low != null && low > 0) {
            models = this.modelRepository.findByBrandIdAndLower(brandId, low);
        } else {
            models = this.modelRepository.findByBrandId(brandId);
        }
        if (models == null || models.isEmpty()) {
            throw new GeneralControlledException("No se encontraron resultados para el id consultado");
        } else {
            return ModelMapper.entitiesToResponses(models);
        }
    }

    @Override
    public BrandResponse saveBrand(NewBrandRequest request) {
        Brand brand = this.repository.findByName(request.getNombre().trim());
        if (brand != null) {
            throw new GeneralControlledException("La marca ya existe");
        }
        brand = BrandMapper.requestToEntity(request);
        brand = this.repository.save(brand);
        return BrandMapper.entityToResponse(brand);
    }

    @Override
    public ModelResponse saveModel(String id, NewModelRequest request) {
        Long brandId = this.stringToLong(id);
        Optional<Brand> optBrand = this.repository.findById(brandId);
        if (optBrand.isEmpty()) {
            throw new GeneralControlledException("La marca a afectar no existe");
        }
        List<Model> models = this.modelRepository.findByBrandIdAndName(brandId, request.getName().trim());
        if (models != null && !models.isEmpty()) {
            throw new GeneralControlledException("El modelo ya exise en esta marca");
        }
        Model model = ModelMapper.requestToEntity(request);
        model.setBrandId(brandId);
        model = this.modelRepository.save(model);
        return ModelMapper.entityToResponse(model);
    }

    private Long stringToLong(String id) {
        try {
            return id != null ? Long.valueOf(id) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
