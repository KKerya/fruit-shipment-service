package com.example.shipment.service;

import com.example.shipment.domain.Delivery;
import com.example.shipment.domain.DeliveryItem;
import com.example.shipment.domain.Product;
import com.example.shipment.domain.Supplier;
import com.example.shipment.dto.DeliveryItemResponseDto;
import com.example.shipment.dto.DeliveryRequestDto;
import com.example.shipment.dto.DeliveryItemRequestDto;
import com.example.shipment.dto.DeliveryResponseDto;
import com.example.shipment.repository.DeliveryRepository;
import com.example.shipment.repository.ProductRepository;
import com.example.shipment.repository.SupplierRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DeliveryServiceImpl implements DeliveryService {
    private final SupplierRepository supplierRepository;
    private final DeliveryRepository deliveryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public DeliveryServiceImpl(SupplierRepository supplierRepository, DeliveryRepository deliveryRepository, ProductRepository productRepository) {
        this.supplierRepository = supplierRepository;
        this.deliveryRepository = deliveryRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public DeliveryResponseDto acceptDelivery(DeliveryRequestDto request) {
        Supplier supplier = supplierRepository.findById(request.supplierId())
                .orElseThrow( () -> new EntityNotFoundException("Supplier not found"));

        Delivery delivery = new Delivery(supplier, request.deliveryDate());

        for (DeliveryItemRequestDto item : request.deliveryItems()) {
            Product product = productRepository.findById(item.productId())
                    .orElseThrow(() -> new EntityNotFoundException("Product not found"));
            delivery.addDeliveryItem(new DeliveryItem(delivery, product, item.weightKg()));
        }

        Delivery saved = deliveryRepository.save(delivery);

        List<DeliveryItemResponseDto> itemsResponse = saved.getDeliveryItems().stream()
                .map(item -> new DeliveryItemResponseDto(
                        item.getProduct().getName(),
                        item.getProduct().getProductType().getDisplayName(),
                        item.getWeightKg(),
                        item.getTotalPrice()
                ))
                .toList();

        return new DeliveryResponseDto(
                saved.getId(),
                saved.getDeliveryDate(),
                saved.getSupplier().getName(),
                itemsResponse
        );
    }
}
