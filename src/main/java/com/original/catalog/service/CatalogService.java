package com.original.catalog.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.original.catalog.dto.CatalogDto;
import com.original.catalog.entities.Catalog;
import com.original.catalog.repository.CatalogRepository;

import jakarta.transaction.Transactional;

@Service
public class CatalogService {

    @Autowired
    private CatalogRepository catalogRepository;

    @Transactional
    public Catalog createCatalog(CatalogDto dto) {
        try {
            Catalog newCatalog = catalogRepository.save(new Catalog(null, dto.categoryId(), dto.mediaId(), dto.mediaTypeId(), dto.classificationId(), dto.participantId(), dto.mediaPath(), dto.price(), LocalDate.now(), dto.inactivationDate()));
            return newCatalog;

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
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
            Catalog newCatalog = catalogRepository.save(new Catalog(id, dto.categoryId(), dto.mediaId(), dto.mediaTypeId(), dto.classificationId(), dto.participantId(), dto.mediaPath(), dto.price(), LocalDate.now(), dto.inactivationDate()));
            return newCatalog;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
