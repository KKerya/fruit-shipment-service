package com.example.shipment.service;

import com.example.shipment.domain.Delivery;
import com.example.shipment.dto.ReportDto;

import java.util.List;

public interface ReportService {
    List<ReportDto> getReport(List<Delivery> deliveries);
}
