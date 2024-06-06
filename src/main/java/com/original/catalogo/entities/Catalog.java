package com.original.catalogo.entities;
import java.sql.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "catalog")
public class Catalog{
    @Id
    @Column(name = "id", length = 64, updatable = false)
    private String id;
    @Column(name = "categoryId", length = 64, updatable = true)
    private String categoryId;
    @Column(name = "mediaId", length = 64, updatable = true)
    private String mediaId;
    @Column(name = "mediaTypeId", length = 64, updatable = true)
    private String mediaTypeId;
    @Column(name = "classificaitonId", length = 64, updatable = true)
    private String classificaitonId;
    @Column(name = "participantId", length = 64, updatable = true)
    private String participantId;
    @Column(length = 300, nullable = false)
    private String mediaPath;
    @Column(nullable = false)
    private Float price;
    @Column(nullable = false)
    private Date dataAlteracao;
    @Column(nullable = false)
    private Date dataInativacao;
}