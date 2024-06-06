package com.original.catalog.service;

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
            Catalog newCatalog = catalogRepository.save(new Catalog(null, dto.categoryId(), dto.mediaId(), dto.mediaTypeId(), dto.classificaitonId(), dto.participantId(), dto.mediaPath(), dto.price(), dto.dataAlteracao(), dto.dataInativacao()));
            return newCatalog;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
}
