package com.example.shipment.service;

import com.example.shipment.domain.Product;
import com.example.shipment.domain.Supplier;
import com.example.shipment.repository.ProductRepository;
import com.example.shipment.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogServiceImpl implements CatalogService {
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CatalogServiceImpl(SupplierRepository supplierRepository, ProductRepository productRepository) {
        this.supplierRepository = supplierRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<Supplier> getSuppliers() {
        return supplierRepository.findAll();
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }
}