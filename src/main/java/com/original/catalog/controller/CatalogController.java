package com.original.catalog.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.original.catalog.dto.CatalogDto;
import com.original.catalog.entities.Catalog;
import com.original.catalog.service.CatalogService;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    @PostMapping
    public ResponseEntity<ApiResponse> createSpecie(@RequestBody CatalogDto dto) {
        Optional<Catalog> catalog = catalogService.findCatalogById(dto.id());
        if (catalog.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("Catalago já cadastrado."));
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<Catalog>("Created", catalogService.createCatalog(dto)));
    }
}
