package com.original.catalog.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping
    public ResponseEntity<ApiResponse<List<Catalog>>> listCatalog() {
        List<Catalog> listCatalog = catalogService.listCatalog();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<List<Catalog>>("Ok", listCatalog));
    }

    @GetMapping("/name")
    public ResponseEntity<ApiResponse<List<Catalog>>> listCatalogName() {
        List<Catalog> listCatalogName = catalogService.listCatalogName();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<List<Catalog>>("Ok", listCatalogName));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Catalog>> findCatalog(@PathVariable String id) {
        Catalog catalog = catalogService.findCatalog(id);
        if (catalog == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<Catalog>("Ok", catalog));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCatalog(@PathVariable String id) {
        Catalog catalog = catalogService.findCatalog(id);
        if (catalog == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        catalogService.deleteCatalog(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Deleted"));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse> updateCatalog(@PathVariable String id, @RequestBody CatalogDto dto) {
        Catalog catalog = catalogService.findCatalog(id);
        if (catalog == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Espécie não encontrada."));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<Catalog>("Updated", catalogService.updateCatalogById(id, dto)));
    }
}
