package com.nexu.restapi.test;

import com.nexu.restapi.custom.exceptions.GeneralControlledException;
import com.nexu.restapi.custom.mappers.BrandMapper;
import com.nexu.restapi.entities.Brand;
import com.nexu.restapi.entities.Model;
import com.nexu.restapi.repositories.BrandRepository;
import com.nexu.restapi.repositories.ModelRepository;
import com.nexu.restapi.requests.NewBrandRequest;
import com.nexu.restapi.requests.NewModelRequest;
import com.nexu.restapi.responses.BrandResponse;
import com.nexu.restapi.responses.ModelResponse;
import com.nexu.restapi.serviceImpl.BrandServiceImpl;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class BrandServiceImplTest {

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private ModelRepository modelRepository;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private BrandServiceImpl brandService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getBrands_ShouldReturnBrandResponses() {
        List<Brand> brands = Arrays.asList(new Brand(), new Brand());
        when(brandRepository.findAllBrandsWithAveragePrice()).thenReturn(brands);
        List<BrandResponse> expectedResponses = BrandMapper.entitiesToResponses(brands);

        List<BrandResponse> actualResponses = brandService.getBrands();

        assertNotNull(actualResponses);
        assertEquals(expectedResponses.size(), actualResponses.size());
        verify(brandRepository).findAllBrandsWithAveragePrice();
    }

    @Test
    void saveBrand_NewBrand_Success() {
        NewBrandRequest request = new NewBrandRequest();
        request.setNombre("NewBrand");
        when(brandRepository.findByName(request.getNombre())).thenReturn(null);
        Brand savedBrand = new Brand();
        when(brandRepository.save(any(Brand.class))).thenReturn(savedBrand);

        BrandResponse response = brandService.saveBrand(request);

        assertNotNull(response);
        verify(brandRepository).findByName(request.getNombre());
        verify(brandRepository).save(any(Brand.class));
    }

    @Test
    void saveBrand_BrandAlreadyExists_ThrowsException() {
        NewBrandRequest request = new NewBrandRequest();
        request.setNombre("ExistingBrand");
        when(brandRepository.findByName(request.getNombre())).thenReturn(new Brand());

        assertThrows(GeneralControlledException.class, () -> brandService.saveBrand(request));

        verify(brandRepository).findByName(request.getNombre());
        verify(brandRepository, never()).save(any(Brand.class));
    }

    @Test
    void getModels_WithFiltersShouldReturnModels() {
        String id = "1";
        String greater = "100000";
        String lower = "500000";
        when(brandRepository.findById(Long.valueOf(id))).thenReturn(Optional.of(new Brand()));
        List<Model> models = Arrays.asList(new Model(), new Model());
        when(modelRepository.findByBrandIdAndFilters(Long.valueOf(id), Long.valueOf(greater), Long.valueOf(lower))).thenReturn(models);

        List<ModelResponse> modelResponses = brandService.getModels(id, greater, lower);

        assertNotNull(modelResponses);
        assertEquals(2, modelResponses.size());
        verify(brandRepository).findById(Long.valueOf(id));
        verify(modelRepository).findByBrandIdAndFilters(Long.valueOf(id), Long.valueOf(greater), Long.valueOf(lower));
    }

    @Test
    void getModels_WithoutFiltersShouldReturnModels() {
        String id = "1";
        Brand brand = new Brand();
        brand.setId(Long.valueOf(id));
        when(brandRepository.findById(Long.valueOf(id))).thenReturn(Optional.of(brand));
        List<Model> models = Arrays.asList(new Model());
        when(modelRepository.findByBrandId(Long.valueOf(id))).thenReturn(models);

        List<ModelResponse> modelResponses = brandService.getModels(id, null, null);

        assertNotNull(modelResponses);
        assertEquals(1, modelResponses.size());
        verify(brandRepository).findById(Long.valueOf(id));
        verify(modelRepository).findByBrandId(Long.valueOf(id));
    }


    @Test
    void saveModel_ShouldSaveModel() {
        String id = "1";
        NewModelRequest request = new NewModelRequest();
        request.setName("ModelName");
        Optional<Brand> brand = Optional.of(new Brand());
        brand.get().setId(Long.valueOf(id));
        when(brandRepository.findById(Long.valueOf(id))).thenReturn(brand);
        when(modelRepository.findByBrandIdAndName(Long.valueOf(id), request.getName().trim())).thenReturn(Arrays.asList());

        Model model = new Model();
        model.setBrandId(Long.valueOf(id));
        when(modelRepository.save(any(Model.class))).thenReturn(model);

        ModelResponse modelResponse = brandService.saveModel(id, request);

        assertNotNull(modelResponse);
        verify(brandRepository).findById(Long.valueOf(id));
        verify(modelRepository).save(any(Model.class));
    }

    @Test
    void saveModel_ModelAlreadyExistsThrowsException() {
        String id = "1";
        NewModelRequest request = new NewModelRequest();
        request.setName("ModelName");
        Brand brand = new Brand();
        brand.setId(Long.valueOf(id));
        when(brandRepository.findById(Long.valueOf(id))).thenReturn(Optional.of(brand));
        List<Model> existingModels = Arrays.asList(new Model());
        when(modelRepository.findByBrandIdAndName(Long.valueOf(id), request.getName().trim())).thenReturn(existingModels);

        assertThrows(GeneralControlledException.class, () -> brandService.saveModel(id, request));

        verify(brandRepository).findById(Long.valueOf(id));
        verify(modelRepository).findByBrandIdAndName(Long.valueOf(id), request.getName().trim());
    }
}
