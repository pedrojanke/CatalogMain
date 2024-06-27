package com.original.catalog.dto;

import java.time.LocalDate;

public record CatalogDto(String id, String category, String media, String mediaType, String classification, String participant, String mediaPath, Float price, LocalDate registrionDate, LocalDate inactivationDate) {
}
