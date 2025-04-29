package com.buysmart.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orders") // Use a custom table name to avoid conflict with the reserved keyword
public class Order {
    @Id
    private Long id;
    private Long userId;
    private Long productId;
    private int quantity;
    private double totalPrice;

    // Getters and Setters
}
