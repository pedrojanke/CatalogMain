package com.original.catalog.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.original.catalog.dto.CatalogDto;
import com.original.catalog.entities.Catalog;
import com.original.catalog.repository.CatalogRepository;
import com.original.catalog.tests.IdWithName;

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

    public List<Catalog> listCatalogName() {
        try {
            List<Catalog> catalogs = catalogRepository.findAll();
    
            for (Catalog catalog : catalogs) {
                catalog.setCategory(getIdWithNameFromEndpoint("http://localhost:8081/api/v1/category/", catalog.getCategoryId()));
                catalog.setMedia(getIdWithNameFromEndpoint("http://localhost:8081/api/v1/media/", catalog.getMediaId()));
                catalog.setMediaType(getIdWithNameFromEndpoint("http://localhost:8081/api/v1/mediatype/", catalog.getMediaTypeId()));
                catalog.setClassification(getIdWithNameFromEndpoint("http://localhost:8081/api/v1/classification/", catalog.getClassificationId()));
                catalog.setParticipant(getIdWithNameFromEndpoint("http://localhost:8081/api/v1/participant/", catalog.getParticipantId()));
            }
    
            return catalogs;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar catálogos: " + e.getMessage());
        }
    }

    // Compara o ID e pega o nome que representa cada ID dos endpoints
    private IdWithName getIdWithNameFromEndpoint(String baseUrl, String id) {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(baseUrl + id, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode root = objectMapper.readTree(response.getBody());
                JsonNode data = root.get("data");
                if (data != null) {
                    JsonNode nameNode = data.get("name");
                    if (nameNode != null) {
                        String name = nameNode.asText();
                        return new IdWithName(name, id);
                    }
                }
                return new IdWithName("Nome não encontrado", id);
            } else {
                return new IdWithName("Nome não encontrado", id);
            }
        } catch (HttpClientErrorException.NotFound e) {
            return new IdWithName("ID não encontrado", id);
        } catch (IOException e) {
            return new IdWithName("Erro ao processar resposta", id);
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
