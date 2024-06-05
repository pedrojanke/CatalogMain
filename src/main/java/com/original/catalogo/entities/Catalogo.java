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
public class Catalogo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    @Column(nullable = false)
    private UUID categoryId;
    @Column(nullable = false)
    private UUID mediaId;
    @Column(nullable = false)
    private UUID mediaTypeId;
    @Column(nullable = false)
    private UUID classificaitonId;
    @Column(nullable = false)
    private UUID participantId;
    @Column(length = 300, nullable = false)
    private String mediaPath;
    @Column(nullable = false)
    private Float price;
    @Column(nullable = false)
    private Date dataAlteracao;
    @Column(nullable = false)
    private Date dataInativacao;
}