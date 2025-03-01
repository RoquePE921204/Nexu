package com.nexu.restapi.integration;

import com.nexu.restapi.RestapiApplication;
import com.nexu.restapi.custom.exceptions.GeneralControlledException;
import com.nexu.restapi.entities.Brand;
import com.nexu.restapi.entities.Model;
import com.nexu.restapi.repositories.BrandRepository;
import com.nexu.restapi.repositories.ModelRepository;
import com.nexu.restapi.requests.NewBrandRequest;
import com.nexu.restapi.requests.NewModelRequest;
import com.nexu.restapi.responses.BrandResponse;
import com.nexu.restapi.responses.ModelResponse;
import com.nexu.restapi.services.BrandService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = RestapiApplication.class)
@Transactional
@ActiveProfiles("test")
public class BrandServiceImplIntegrationTest {

    @Autowired
    private BrandService brandService;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ModelRepository modelRepository;

    private Long brandId;
    private Long brandIdNoModel;

    @BeforeEach
    void setup() {
        modelRepository.deleteAll();
        brandRepository.deleteAll();

        Brand brand = new Brand();
        brand.setNombre("Test Brand");
        brand = brandRepository.save(brand);

        Model model = new Model();
        model.setName("Existing Model Name");
        model.setAveragePrice(123456.0);
        model.setBrandId(brand.getId());
        model = modelRepository.save(model);

        Brand brandNoModel = new Brand();
        brandNoModel.setNombre("Test Brand 2");
        brandNoModel = brandRepository.save(brandNoModel);

        this.brandId = brand.getId();
        this.brandIdNoModel = brandNoModel.getId();
    }

    @Test
    void getBrands_ShouldReturnBrandResponses() {
        List<BrandResponse> responses = brandService.getBrands();
        assertFalse(responses.isEmpty());
        assertTrue(responses.stream().anyMatch(br -> br.getNombre().equals("Test Brand")));
    }

    @Test
    void saveBrand_NewBrand_Success() {
        NewBrandRequest request = new NewBrandRequest();
        request.setNombre("New Unique Brand");

        BrandResponse response = brandService.saveBrand(request);
        assertNotNull(response);
        assertNotNull(response.getId());
    }

    @Test
    void saveBrand_BrandAlreadyExists_ThrowsException() {
        NewBrandRequest request = new NewBrandRequest();
        request.setNombre("Test Brand");

        assertThrows(GeneralControlledException.class, () -> brandService.saveBrand(request));
    }

    @Test
    void getModels_WithFiltersShouldReturnModels() {
        List<ModelResponse> models = brandService.getModels(String.valueOf(brandId), "100000", "200000");
        assertFalse(models.isEmpty());
    }

    @Test
    void getModels_WithoutFiltersShouldReturnModels() {
        List<ModelResponse> models = brandService.getModels(String.valueOf(brandId), null, null);
        assertFalse(models.isEmpty());
    }

    @Test
    void saveModel_ShouldSaveModel() {
        NewModelRequest request = new NewModelRequest();
        request.setName("New Model");
        request.setAveragePrice(150000.0);

        ModelResponse response = brandService.saveModel(String.valueOf(brandId), request);
        assertNotNull(response);
        assertNotNull(response.getId());
    }

    @Test
    void saveModel_ModelAlreadyExistsThrowsException() {
        NewModelRequest request = new NewModelRequest();
        request.setName("Existing Model Name");

        assertThrows(GeneralControlledException.class, () -> brandService.saveModel(String.valueOf(brandId), request));
    }
}
