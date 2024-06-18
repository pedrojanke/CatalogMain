package com.original.catalog.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.original.catalog.dto.CatalogDto;
import com.original.catalog.entities.Catalog;
import com.original.catalog.repository.CatalogRepository;

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

    @Transactional
    public Catalog createCatalog(CatalogDto dto) {
        try {
            validateId("http://localhost:8081/api/v1/category/", dto.categoryId());
            validateId("http://localhost:8081/api/v1/media/", dto.mediaId());
            validateId("http://localhost:8081/api/v1/mediatype/", dto.mediaTypeId());
            validateId("http://localhost:8081/api/v1/classification/", dto.classificationId());
            validateId("http://localhost:8081/api/v1/participant/", dto.participantId());

            Catalog newCatalog = catalogRepository.save(new Catalog(null, dto.categoryId(), dto.mediaId(),
                    dto.mediaTypeId(), dto.classificationId(), dto.participantId(), dto.mediaPath(), dto.price(),
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

            validateId("http://localhost:8081/api/v1/category/", dto.categoryId());
            validateId("http://localhost:8081/api/v1/media/", dto.mediaId());
            validateId("http://localhost:8081/api/v1/mediatype/", dto.mediaTypeId());
            validateId("http://localhost:8081/api/v1/classification/", dto.classificationId());
            validateId("http://localhost:8081/api/v1/participant/", dto.participantId());

            Catalog newCatalog = catalogRepository.save(new Catalog(id, dto.categoryId(), dto.mediaId(),
                    dto.mediaTypeId(), dto.classificationId(), dto.participantId(), dto.mediaPath(), dto.price(),
                    LocalDate.now(), dto.inactivationDate()));
            return newCatalog;
        } catch (HttpClientErrorException.NotFound e) {
            throw new IllegalArgumentException("Um ou mais IDs fornecidos não são válidos.");
        } catch (Exception e) {
            throw new RuntimeException("Ocorreu um erro ao criar o catálogo: " + e.getMessage());
        }
    }

}
