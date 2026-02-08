package com.example.shipment;

import com.example.shipment.domain.*;
import com.example.shipment.dto.ReportDto;
import com.example.shipment.service.ReportServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ReportServiceITest {
    @InjectMocks
    private ReportServiceImpl reportService;

    @Test
    void buildReportShouldAggregateWeightAndCost() {
        Supplier supplier = new Supplier("Supplier 1");

        Product product = new Product(
                "Pear White",
                new BigDecimal("8.00"),
                ProductType.PEAR,
                supplier
        );
        ReflectionTestUtils.setField(product, "id", 11L);

        Delivery firstDelivery = new Delivery(supplier, LocalDate.of(2026, 1, 1));
        firstDelivery.addDeliveryItem(new DeliveryItem(firstDelivery, product, new BigDecimal("3.00")));

        Delivery secondDelivery = new Delivery(supplier, LocalDate.of(2026, 1, 15));
        secondDelivery.addDeliveryItem(new DeliveryItem(secondDelivery, product, new BigDecimal("2.00")));

        List<ReportDto> report = reportService.buildReport(List.of(firstDelivery, secondDelivery));

        assertThat(report).hasSize(1);

        ReportDto first = report.get(0);

        assertThat(first.supplierName()).isEqualTo("Supplier 1");
        assertThat(first.productName()).isEqualTo("Pear White");
        assertThat(first.productType()).isEqualTo("Груша");
        assertThat(first.totalWeight()).isEqualByComparingTo("5.00");
        assertThat(first.totalCost()).isEqualByComparingTo("40.00");
    }

    @Test
    void buildReport_shouldNotMixDifferentProducts() {
        Supplier supplier = new Supplier("Supplier 1");

        Product pear = new Product("Pear", new BigDecimal("8.00"), ProductType.PEAR, supplier);
        Product apple = new Product("Apple", new BigDecimal("10.00"), ProductType.APPLE, supplier);

        Delivery delivery = new Delivery(supplier, LocalDate.now());
        delivery.addDeliveryItem(new DeliveryItem(delivery, pear, new BigDecimal("2.0")));
        delivery.addDeliveryItem(new DeliveryItem(delivery, apple, new BigDecimal("1.0")));

        List<ReportDto> report =
                reportService.buildReport(List.of(delivery));

        assertThat(report).hasSize(2);

        ReportDto first = report.get(0);
        ReportDto second = report.get(1);

        assertThat(first.supplierName()).isEqualTo("Supplier 1");
        assertThat(first.productName()).isEqualTo("Pear");
        assertThat(first.productType()).isEqualTo("Груша");
        assertThat(first.totalWeight()).isEqualByComparingTo("2.0");
        assertThat(first.totalCost()).isEqualByComparingTo("16.0");

        assertThat(second.supplierName()).isEqualTo("Supplier 1");
        assertThat(second.productName()).isEqualTo("Apple");
        assertThat(second.productType()).isEqualTo("Яблоко");
        assertThat(second.totalWeight()).isEqualByComparingTo("1.0");
        assertThat(second.totalCost()).isEqualByComparingTo("10.0");
    }
}