package com.example.shipment.controller;

import com.example.shipment.dto.DeliveryRequestDto;
import com.example.shipment.dto.DeliveryResponseDto;
import com.example.shipment.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {
    private final DeliveryService deliveryService;

    @Autowired
    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public DeliveryResponseDto acceptDelivery(@RequestBody DeliveryRequestDto deliveryRequestDto){
        return deliveryService.acceptDelivery(deliveryRequestDto);
    }
}
