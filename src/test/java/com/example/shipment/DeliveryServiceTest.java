package com.example.shipment;

import com.example.shipment.domain.Delivery;
import com.example.shipment.domain.Product;
import com.example.shipment.domain.ProductType;
import com.example.shipment.domain.Supplier;
import com.example.shipment.dto.DeliveryItemRequestDto;
import com.example.shipment.dto.DeliveryRequestDto;
import com.example.shipment.dto.DeliveryResponseDto;
import com.example.shipment.repository.DeliveryRepository;
import com.example.shipment.repository.ProductRepository;
import com.example.shipment.repository.SupplierRepository;
import com.example.shipment.service.DeliveryService;
import com.example.shipment.service.DeliveryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeliveryServiceTest {
    @Mock
    private SupplierRepository supplierRepository;

    @Mock
    private DeliveryRepository deliveryRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private DeliveryServiceImpl deliveryService;

    @Test
    void acceptDeliveryCreatesDelivery() {
        Supplier supplier = new Supplier("Supplier 1");
        ReflectionTestUtils.setField(supplier, "id", 1L);

        Product product = new Product("Apple Red", new BigDecimal("10.00"), ProductType.APPLE, supplier);
        ReflectionTestUtils.setField(product, "id", 10L);

        DeliveryRequestDto request = new DeliveryRequestDto(
                1L,
                LocalDate.of(2026, 1, 1),
                List.of(new DeliveryItemRequestDto(10L, new BigDecimal("3.50")))
        );

        when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));
        when(productRepository.findById(10L)).thenReturn(Optional.of(product));
        when(deliveryRepository.save(any())).thenAnswer( inv -> {
            Delivery delivery = inv.getArgument(0, Delivery.class);
            ReflectionTestUtils.setField(delivery, "id", 70L);
            return delivery;
        });

        DeliveryResponseDto response = deliveryService.acceptDelivery(request);

        assertThat(response.deliveryId()).isEqualTo(70L);
        assertThat(response.supplierName()).isEqualTo("Supplier 1");
        assertThat(response.deliveryDate()).isEqualTo(LocalDate.of(2026, 1, 1));
        assertThat(response.items().size()).isEqualTo(1);
        assertThat(response.items().get(0).productName()).isEqualTo("Apple Red");
        assertThat(response.items().get(0).productType()).isEqualTo("Яблоко");
        assertThat(response.items().get(0).totalPrice()).isEqualByComparingTo("35.00");
        assertThat(response.items().get(0).weightKg()).isEqualByComparingTo("3.50");
    }
}
