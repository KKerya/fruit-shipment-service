package com.example.shipment.dto;

import java.time.LocalDate;
import java.util.List;

public record DeliveryRequestDto(
        Long supplierId,
        LocalDate deliveryDate,
        List<DeliveryItemRequestDto> deliveryItems
) {}