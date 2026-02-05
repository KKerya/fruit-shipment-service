package com.example.shipment.dto;

import java.math.BigDecimal;

public record DeliveryItemResponseDto(
        String productName,
        String productType,
        BigDecimal weightKg,
        BigDecimal totalPrice
) {}