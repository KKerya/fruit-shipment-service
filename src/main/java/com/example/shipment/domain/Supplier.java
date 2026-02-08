package com.example.shipment.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_suppliers")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;

    public Supplier() {
        this.products = new ArrayList<>();
    }

    public Supplier(String name) {
        this.name = name;
        this.products = new ArrayList<>();
    }

    public Supplier(String name, List<Product> products) {
        this.name = name;
        this.products = products == null ? new ArrayList<>() : new ArrayList<>(products);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
