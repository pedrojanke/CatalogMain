package com.original.catalog.dto;

import java.time.LocalDate;
import java.util.Date;

public record CatalogDto(String id, String categoryId, String mediaId, String mediaTypeId, String classificationId, String participantId, String mediaPath, Float price, LocalDate registrionDate, Date inactivationDate) {
}
