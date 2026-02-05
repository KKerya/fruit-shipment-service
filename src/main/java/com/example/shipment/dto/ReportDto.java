package com.example.shipment.dto;

import java.math.BigDecimal;

public record ReportDto (
        String supplierName,
        String productName,
        String productType,
        BigDecimal totalWeight,
        BigDecimal totalCost
) { }