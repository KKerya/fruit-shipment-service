package com.example.shipment.service;

import com.example.shipment.dto.DeliveryRequestDto;
import com.example.shipment.dto.DeliveryResponseDto;
import org.springframework.transaction.annotation.Transactional;

public interface DeliveryService {
    @Transactional
    DeliveryResponseDto acceptDelivery(DeliveryRequestDto request);
}
