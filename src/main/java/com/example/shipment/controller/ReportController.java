package com.example.shipment.controller;

import com.example.shipment.dto.ReportDto;
import com.example.shipment.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**
 * REST-контроллер для получения отчётов по поставкам.
 */
@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * Возвращает отчёт по поставкам за указанный период.
     * @param start дата начала периода
     * @param end дата окончания периода
     * @return список строк отчёта
     */
    @GetMapping("/deliveries")
    public List<ReportDto> getReport(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end)
    {
        return reportService.getReport(start, end);
    }
}
