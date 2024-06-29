package com.original.catalog.dto;

import java.time.LocalDate;

public class CatalogResponseDto {
    public String id;
    public CategoryDto category;
    public MediaDto media;
    public MediaTypeDto mediaType;
    public ClassificationDto classification;
    public ParticipantDto participant;
    public String mediaPath;
    public Float price;
    public LocalDate registrationDate;
    public LocalDate inactivationDate;

    public CatalogResponseDto(
        String id,
        CategoryDto category,
        MediaDto media,
        MediaTypeDto mediaType,
        ClassificationDto classification,
        ParticipantDto participant,
        String mediaPath,
        Float price,
        LocalDate registrationDate,
        LocalDate inactivationDate
    ) {
        this.id = id;
        this.category = category;
        this.media = media;
        this.mediaType = mediaType;
        this.classification = classification;
        this.participant = participant;
        this.mediaPath = mediaPath;
        this.price = price;
        this.registrationDate = registrationDate;
        this.inactivationDate = inactivationDate;
    }
}
