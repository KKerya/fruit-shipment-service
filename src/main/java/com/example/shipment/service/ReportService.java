package com.example.shipment.service;

import com.example.shipment.domain.Delivery;
import com.example.shipment.dto.ReportDto;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {
    List<ReportDto> getReport(LocalDate start, LocalDate end);

    List<ReportDto> buildReport(List<Delivery> deliveries);
}
