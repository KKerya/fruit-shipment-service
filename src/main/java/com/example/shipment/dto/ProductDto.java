package com.example.shipment.dto;

import com.example.shipment.domain.ProductType;

public record ProductDto (
        Long id,
        String name,
        ProductType productType
) { }
