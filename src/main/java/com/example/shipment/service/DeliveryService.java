package com.example.shipment.service;

import com.example.shipment.domain.Delivery;
import com.example.shipment.dto.DeliveryRequestDto;
import com.example.shipment.dto.DeliveryResponseDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DeliveryService {
    @Transactional
    DeliveryResponseDto acceptDelivery(DeliveryRequestDto request);

    List<Delivery> getRecentDeliveries();
}
