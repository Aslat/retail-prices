package com.example.Prices.infrastructure.H2DB;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "BRANDS")
public class Brand {

    @Id
    @Column(name = "BRAND_ID")
    private Long id;
    @Column(name = "NAME")
    private String name;
}