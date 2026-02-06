package com.example.shipment.controller;

import com.example.shipment.service.CatalogService;
import com.example.shipment.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;

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
        model.addAttribute("suppliers", catalogService.getSuppliers());
        model.addAttribute("products", catalogService.getProducts());
        model.addAttribute("deliveries", deliveryService.getRecentDeliveries());
        return "deliveries";
    }
}
