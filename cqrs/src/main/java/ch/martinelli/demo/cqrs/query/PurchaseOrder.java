package ch.martinelli.demo.cqrs.query;

import java.time.LocalDateTime;
import java.util.List;

record PurchaseOrder(Long id, LocalDateTime orderDate, Customer customer, List<OrderItem> items) {
}