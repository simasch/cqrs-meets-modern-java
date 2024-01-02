package ch.martinelli.demo.cqrs.api;

import java.time.LocalDateTime;
import java.util.List;

public record PurchaseOrder(Long id, LocalDateTime orderDate, Customer customer, List<OrderItem> items) {
}