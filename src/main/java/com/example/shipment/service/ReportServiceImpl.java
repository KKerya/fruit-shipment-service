package com.example.shipment.service;

import com.example.shipment.domain.Delivery;
import com.example.shipment.domain.DeliveryItem;
import com.example.shipment.dto.ReportDto;
import com.example.shipment.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {
    private final DeliveryRepository deliveryRepository;

    @Autowired
    public ReportServiceImpl(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @Override
    public List<ReportDto> getReport(LocalDate start, LocalDate end) {
        List<Delivery> deliveries = deliveryRepository.findByDeliveryDateBetween(start, end);
        return buildReport(deliveries);
    }

    @Override
    public List<ReportDto> buildReport(List<Delivery> deliveries) {
        Map<String, ReportAccumulator> accumulatorMap = new HashMap<>();

        for (Delivery delivery : deliveries) {
            for (DeliveryItem deliveryItem : delivery.getDeliveryItems()) {
                String key = delivery.getSupplier().getName() + "|" +
                        deliveryItem.getProduct().getName() + "|" +
                        deliveryItem.getProduct().getProductType().getDisplayName();

                accumulatorMap.computeIfAbsent(key, k -> new ReportAccumulator(
                                delivery.getSupplier().getName(),
                                deliveryItem.getProduct().getName(),
                                deliveryItem.getProduct().getProductType().getDisplayName()
                        )
                );

                ReportAccumulator reportAccumulator = accumulatorMap.get(key);
                reportAccumulator.add(deliveryItem.getWeightKg(), deliveryItem.getTotalPrice());
            }
        }

        List<ReportDto> result = new ArrayList<>();
        for (ReportAccumulator reportAccumulator : accumulatorMap.values()) {
            result.add(new ReportDto(
                    reportAccumulator.supplierName,
                    reportAccumulator.productName,
                    reportAccumulator.productType,
                    reportAccumulator.totalWeight,
                    reportAccumulator.totalCost
            ));
        }

        return result;
    }

    private static class ReportAccumulator {
        final String supplierName;
        final String productName;
        final String productType;
        BigDecimal totalWeight = BigDecimal.ZERO;
        BigDecimal totalCost = BigDecimal.ZERO;

        ReportAccumulator(String supplierName, String productName, String productType) {
            this.supplierName = supplierName;
            this.productName = productName;
            this.productType = productType;
        }

        void add(BigDecimal weight, BigDecimal cost) {
            totalWeight = totalWeight.add(weight);
            totalCost = totalCost.add(cost);
        }
    }
}
