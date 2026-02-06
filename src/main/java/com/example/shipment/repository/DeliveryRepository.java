package com.example.shipment.repository;

import com.example.shipment.domain.Delivery;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    @EntityGraph(attributePaths = {"deliveryItems", "deliveryItems.product", "supplier"})
    List<Delivery> findByDeliveryDateBetween(LocalDate start, LocalDate endDate);

    @EntityGraph(attributePaths = {"deliveryItems", "deliveryItems.product", "supplier"})
    List<Delivery> findAllByOrderByDeliveryDate();
}
