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
    @Column(name = "id", length = 64, nullable = false, unique = true)
    private String id;
    
    @Column(name = "category", length = 64, nullable = false)
    private String category;
    
    @Column(name = "media", length = 64, nullable = false)
    private String media;
    
    @Column(name = "mediaType", length = 64, nullable = false)
    private String mediaType;
    
    @Column(name = "classification", length = 64, nullable = false)
    private String classification;
    
    @Column(name = "participant", length = 64, nullable = false)
    private String participant;
    
    @Column(length = 300, nullable = false)
    private String mediaPath;
    
    @Column(nullable = false)
    private Float price;
    
    @Column(nullable = false)
    private LocalDate registrationDate;
    
    @Column(nullable = true)
    private LocalDate inactivationDate;
}
