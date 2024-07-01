package com.original.catalog.service;

import java.net.http.HttpHeaders;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.original.catalog.entities.ApiResponse;


import com.original.catalog.dto.*;
import com.original.catalog.entities.Catalog;
import com.original.catalog.repository.CatalogRepository;
import com.original.catalog.client.*;

import jakarta.transaction.Transactional;

@Service
public class CatalogService {

    @Autowired
    private CatalogRepository catalogRepository;

    @Autowired
    private RestTemplate restTemplate;

    private void validateId(String url, String id) {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url + id, String.class);
            if (response.getStatusCode().isError()) {
                throw new HttpClientErrorException(response.getStatusCode());
            }
        } catch (HttpClientErrorException.NotFound e) {
            throw new IllegalArgumentException("O ID '" + id + "' fornecido não é válido para " + url);
        }
    }

    public CategoryDto findCategoryById(String id) {
        try {
            ResponseEntity<ApiResponse<CategoryDto>> response = restTemplate.exchange(
                    "http://localhost:8081/api/v1/category/" + id,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<ApiResponse<CategoryDto>>() {});
            return response.getBody().getData();
        } catch (HttpClientErrorException.NotFound e) {
            throw new IllegalArgumentException("O ID '" + id + "' fornecido não é válido para categoria");
        }
    }

    private MediaDto findMediaById(String id) {
        try {
            ResponseEntity<ApiResponse<MediaDto>> response = restTemplate.exchange(
                    "http://localhost:8081/api/v1/media/" + id,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<ApiResponse<MediaDto>>() {});
            return response.getBody().getData();
        } catch (HttpClientErrorException.NotFound e) {
            throw new IllegalArgumentException("O ID '" + id + "' fornecido não é válido para mídia");
        }
    }

    private MediaTypeDto findMediaTypeById(String id) {
        try {
            ResponseEntity<ApiResponse<MediaTypeDto>> response = restTemplate.exchange(
                    "http://localhost:8081/api/v1/mediatype/" + id,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<ApiResponse<MediaTypeDto>>() {});
            return response.getBody().getData();
        } catch (HttpClientErrorException.NotFound e) {
            throw new IllegalArgumentException("O ID '" + id + "' fornecido não é válido para tipo de mídia");
        }
    }

    private ClassificationDto findClassificationById(String id) {
        try {
            ResponseEntity<ApiResponse<ClassificationDto>> response = restTemplate.exchange(
                    "http://localhost:8081/api/v1/classification/" + id,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<ApiResponse<ClassificationDto>>() {});
            return response.getBody().getData();
        } catch (HttpClientErrorException.NotFound e) {
            throw new IllegalArgumentException("O ID '" + id + "' fornecido não é válido para classificação");
        }
    }

    private ParticipantDto findParticipantById(String id) {
        try {
            ResponseEntity<ApiResponse<ParticipantDto>> response = restTemplate.exchange(
                    "http://localhost:8081/api/v1/participant/" + id,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<ApiResponse<ParticipantDto>>() {});
            return response.getBody().getData();
        } catch (HttpClientErrorException.NotFound e) {
            throw new IllegalArgumentException("O ID '" + id + "' fornecido não é válido para participante");
        }
    }

    @Transactional
    public Catalog createCatalog(CatalogDto dto) {
        try {
            validateId("http://localhost:8081/api/v1/category/", dto.category());
            validateId("http://localhost:8081/api/v1/media/", dto.media());
            validateId("http://localhost:8081/api/v1/mediatype/", dto.mediaType());
            validateId("http://localhost:8081/api/v1/classification/", dto.classification());
            validateId("http://localhost:8081/api/v1/participant/", dto.participant());

            Catalog newCatalog = catalogRepository.save(new Catalog(null, dto.category(), dto.media(),
                    dto.mediaType(), dto.classification(), dto.participant(), dto.mediaPath(), dto.price(),
                    LocalDate.now(), dto.inactivationDate()));
            return newCatalog;

        } catch (HttpClientErrorException.NotFound e) {
            throw new IllegalArgumentException("Um ou mais IDs fornecidos não são válidos.");
        } catch (Exception e) {
            throw new RuntimeException("Ocorreu um erro ao criar o catálogo: " + e.getMessage());
        }
    }

    public Optional<Catalog> findCatalogById(String id) {
        try {
            Optional<Catalog> catalog = catalogRepository.findById(id);
            return catalog;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Catalog> listCatalog() {
        try {
            List<Catalog> catalog = catalogRepository.findAll();
            return catalog;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Catalog findCatalog(String id) {
        try {
            Optional<Catalog> catalog = catalogRepository.findById(id);
            if (catalog.isEmpty()) {
                return null;
            }
            return catalog.get();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public CatalogResponseDto findCatalogDetailsById(String id) {
        try {
            Optional<Catalog> catalog = catalogRepository.findById(id);
            if (catalog.isEmpty()) {
                return null;
            }

            Catalog catalogItem = catalog.get();

            String catalogId = catalogItem.getId();
            String categoryId = catalogItem.getCategory();
            String mediaId = catalogItem.getMedia();
            String mediaTypeId = catalogItem.getMediaType();
            String classificationId = catalogItem.getClassification();
            String participantId = catalogItem.getParticipant();
            Float priceValue = catalogItem.getPrice();

            CategoryDto category = this.findCategoryById(categoryId);
            MediaDto media = this.findMediaById(mediaId);
            MediaTypeDto mediaType = this.findMediaTypeById(mediaTypeId);
            ClassificationDto classification = this.findClassificationById(classificationId);
            ParticipantDto participant = this.findParticipantById(participantId);

            SoapNumberToWordClient priceToWords = new SoapNumberToWordClient();
            String priceInWords = priceToWords.execute(priceValue);

            PriceDto price = new PriceDto(priceValue, priceInWords);

            CatalogResponseDto catalogResponse = new CatalogResponseDto(
                catalogId,
                category,
                media,
                mediaType,
                classification,
                participant,
                catalogItem.getMediaPath(),
                price,
                catalogItem.getRegistrationDate(),
                catalogItem.getInactivationDate()
            );

            return catalogResponse;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deleteCatalog(String id) {
        try {
            catalogRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Catalog updateCatalogById(String id, CatalogDto dto) {
        try {
            Optional<Catalog> catalog = catalogRepository.findById(id);
            if (catalog.isEmpty()) {
                return null;
            }

            validateId("http://localhost:8081/api/v1/category/", dto.category());
            validateId("http://localhost:8081/api/v1/media/", dto.media());
            validateId("http://localhost:8081/api/v1/mediatype/", dto.mediaType());
            validateId("http://localhost:8081/api/v1/classification/", dto.classification());
            validateId("http://localhost:8081/api/v1/participant/", dto.participant());

            Catalog newCatalog = catalogRepository.save(new Catalog(id, dto.category(), dto.media(),
                    dto.mediaType(), dto.classification(), dto.participant(), dto.mediaPath(), dto.price(),
                    LocalDate.now(), dto.inactivationDate()));
            return newCatalog;
        } catch (HttpClientErrorException.NotFound e) {
            throw new IllegalArgumentException("Um ou mais IDs fornecidos não são válidos.");
        } catch (Exception e) {
            throw new RuntimeException("Ocorreu um erro ao criar o catálogo: " + e.getMessage());
        }
    }

}