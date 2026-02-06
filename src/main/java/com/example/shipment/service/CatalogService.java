package com.example.shipment.service;

import com.example.shipment.domain.Product;
import com.example.shipment.domain.Supplier;

import java.util.List;

public interface CatalogService {
    List<Supplier> getSuppliers();

    List<Product> getProducts();
}
