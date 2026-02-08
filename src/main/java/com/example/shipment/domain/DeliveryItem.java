package com.example.shipment.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tbl_deliveryItems")
public class DeliveryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column
    private BigDecimal weightKg;

    @Column
    private BigDecimal totalPrice;

    public DeliveryItem() {
    }

    public DeliveryItem(Delivery delivery, Product product, BigDecimal weightKg) {
        this.delivery = delivery;
        this.product = product;
        this.weightKg = weightKg;
        this.totalPrice = product.getPricePerKg().multiply(weightKg);
    }

    public Long getId() {
        return id;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(BigDecimal weightKg) {
        this.weightKg = weightKg;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
