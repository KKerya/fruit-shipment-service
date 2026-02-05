package com.example.shipment.dto;

import java.time.LocalDate;
import java.util.List;

public record DeliveryResponseDto(
        Long deliveryId,
        LocalDate deliveryDate,
        String supplierName,
        List<DeliveryItemResponseDto> items
) {}