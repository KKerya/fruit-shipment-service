package com.example.shipment.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_deliveries")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @Column
    private LocalDate deliveryDate;

    @OneToMany(mappedBy = "delivery", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeliveryItem> deliveryItems;

    public Delivery() {
        this.deliveryItems = new ArrayList<>();
    }

    public Delivery(Supplier supplier, LocalDate deliveryDate) {
        this.supplier = supplier;
        this.deliveryDate = deliveryDate;
        this.deliveryItems = new ArrayList<>();
    }

    public Delivery(Supplier supplier, LocalDate deliveryDate, List<DeliveryItem> deliveryItems) {
        this.supplier = supplier;
        this.deliveryDate = deliveryDate;
        this.deliveryItems = deliveryItems == null ? new ArrayList<>() : new ArrayList<>(deliveryItems);
    }

    public Long getId() {
        return id;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public List<DeliveryItem> getDeliveryItems() {
        return deliveryItems;
    }

    public void setDeliveryItems(List<DeliveryItem> deliveryItems) {
        this.deliveryItems = deliveryItems;
    }

    public void addDeliveryItem(DeliveryItem deliveryItem) {
        if (deliveryItems == null) {
            deliveryItems = new ArrayList<>();
        }
        deliveryItems.add(deliveryItem);
    }

    public void removeDeliveryItem(DeliveryItem deliveryItem) {
        deliveryItems.remove(deliveryItem);
    }

}
