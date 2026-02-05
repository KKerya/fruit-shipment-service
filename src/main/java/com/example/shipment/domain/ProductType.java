package com.example.shipment.domain;

public enum ProductType {
    APPLE("Яблоко"),
    PEAR("Груша");

    private final String displayName;

    ProductType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
