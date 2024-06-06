package com.original.catalog.repository;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.original.catalog.entities.Catalog;

public interface CatalogRepository extends JpaRepository<Catalog, String> {
    @Query("SELECT c FROM catalog c WHERE c.id = ?1")
    Optional<Catalog> findById(String id);
}
