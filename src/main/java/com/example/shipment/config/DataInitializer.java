package com.example.shipment.config;

import com.example.shipment.domain.Delivery;
import com.example.shipment.domain.DeliveryItem;
import com.example.shipment.domain.Product;
import com.example.shipment.domain.ProductType;
import com.example.shipment.domain.Supplier;
import com.example.shipment.repository.DeliveryRepository;
import com.example.shipment.repository.ProductRepository;
import com.example.shipment.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;
    private final DeliveryRepository deliveryRepository;

    @Autowired
    public DataInitializer(SupplierRepository supplierRepository,
                           ProductRepository productRepository,
                           DeliveryRepository deliveryRepository) {
        this.supplierRepository = supplierRepository;
        this.productRepository = productRepository;
        this.deliveryRepository = deliveryRepository;
    }

    @Override
    public void run(String... args) {
        if (supplierRepository.count() > 0) {
            return;
        }

        Supplier supFirst = supplierRepository.save(new Supplier("Садовый двор"));
        Supplier supSecond = supplierRepository.save(new Supplier("Фруктовая долина"));

        Product applePremium = new Product("Яблоко красное", BigDecimal.valueOf(120), ProductType.APPLE, supFirst);
        Product appleGreen = new Product("Яблоко зелёное", BigDecimal.valueOf(105), ProductType.APPLE, supFirst);
        Product pearWilliams = new Product("Груша обычная", BigDecimal.valueOf(135), ProductType.PEAR, supSecond);
        Product pearConference = new Product("Груша вкусная", BigDecimal.valueOf(115), ProductType.PEAR, supSecond);

        productRepository.saveAll(List.of(applePremium, appleGreen, pearWilliams, pearConference));

        Delivery firstDelivery = new Delivery(supFirst, LocalDate.now().minusDays(3));
        firstDelivery.addDeliveryItem(new DeliveryItem(firstDelivery, applePremium, BigDecimal.valueOf(120)));
        firstDelivery.addDeliveryItem(new DeliveryItem(firstDelivery, appleGreen, BigDecimal.valueOf(95)));
        deliveryRepository.save(firstDelivery);

        Delivery secondDelivery = new Delivery(supSecond, LocalDate.now().minusDays(2));
        secondDelivery.addDeliveryItem(new DeliveryItem(secondDelivery, pearWilliams, BigDecimal.valueOf(80)));
        secondDelivery.addDeliveryItem(new DeliveryItem(secondDelivery, pearConference, BigDecimal.valueOf(140)));
        deliveryRepository.save(secondDelivery);

        Delivery thirdDelivery = new Delivery(supFirst, LocalDate.now().minusDays(1));
        thirdDelivery.addDeliveryItem(new DeliveryItem(thirdDelivery, applePremium, BigDecimal.valueOf(60)));
        deliveryRepository.save(thirdDelivery);
    }
}