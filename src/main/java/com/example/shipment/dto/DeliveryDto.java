package com.example.shipment.dto;

import java.time.LocalDate;
import java.util.List;

public record DeliveryDto(
        Long id,
        SupplierDto supplier,
        LocalDate deliveryDate,
        List<DeliveryItemDto> deliveryItems
) {
}
