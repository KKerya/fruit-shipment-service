package com.example.shipment.dto;

import java.math.BigDecimal;

public record DeliveryItemRequestDto(
        Long productId,
        BigDecimal weightKg
) {
}
