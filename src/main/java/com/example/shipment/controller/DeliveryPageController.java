package com.example.shipment.controller;

import com.example.shipment.domain.DeliveryItem;
import com.example.shipment.domain.Supplier;
import com.example.shipment.dto.DeliveryDto;
import com.example.shipment.dto.DeliveryItemDto;
import com.example.shipment.dto.ProductDto;
import com.example.shipment.dto.SupplierDto;
import com.example.shipment.service.CatalogService;
import com.example.shipment.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DeliveryPageController {
    private final CatalogService catalogService;
    private final DeliveryService deliveryService;

    @Autowired
    public DeliveryPageController(CatalogService catalogService, DeliveryService deliveryService) {
        this.catalogService = catalogService;
        this.deliveryService = deliveryService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/deliveries";
    }

    @GetMapping("/deliveries")
    public String deliveriesPage(Model model) {
        List<SupplierDto> suppliers = catalogService.getSuppliers().stream()
                        .map(s -> new SupplierDto(s.getId(), s.getName())).toList();
        List<ProductDto> products = catalogService.getProducts().stream()
                        .map(p -> new ProductDto(p.getId(), p.getName(), p.getProductType())).toList();
        List<DeliveryDto> deliveries = deliveryService.getRecentDeliveries().stream()
                        .map(d -> new DeliveryDto(
                                d.getId(),
                                new SupplierDto(d.getSupplier().getId(), d.getSupplier().getName()),
                                d.getDeliveryDate(),
                                d.getDeliveryItems().stream()
                                        .map(item -> new DeliveryItemDto(
                                                item.getId(),
                                                new ProductDto(item.getProduct().getId(), item.getProduct().getName(), item.getProduct().getProductType()),
                                                item.getWeightKg(),
                                                item.getTotalPrice()
                                        )).toList()
                        )).toList();
        model.addAttribute("suppliers", suppliers);
        model.addAttribute("products", products);
        model.addAttribute("deliveries", deliveries);
        return "deliveries";
    }
}
