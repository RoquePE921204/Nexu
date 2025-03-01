package com.nexu.restapi.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "average_price")
    private Double averagePrice;

    @JoinColumn(nullable = false)
    private Long brandId;
}