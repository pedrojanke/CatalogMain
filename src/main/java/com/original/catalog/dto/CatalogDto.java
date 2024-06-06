package com.original.catalog.dto;

import java.util.Date;

public record CatalogDto(String categoryId, String mediaId, String mediaTypeId, String classificaitonId, String participantId, String mediaPath, Float price, Date dataAlteracao, Date dataInativacao) {
    
}
