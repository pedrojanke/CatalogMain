package com.original.catalog.entities;

import java.time.LocalDate;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
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
public class Catalog {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", length = 64, updatable = false, nullable = false)
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
    
    @Column(length = 300, nullable = true)
    private String mediaPath;
    
    @Column(nullable = true)
    private Float price;
    
    @Column(nullable = true)
    private LocalDate registrationDate;
    
    @Column(nullable = true)
    private LocalDate inactivationDate;
}
