package com.example.shipment.dto;

import java.math.BigDecimal;

public record DeliveryItemDto(
        Long id,
        ProductDto product,
        BigDecimal weightKg,
        BigDecimal totalPrice
) { }
