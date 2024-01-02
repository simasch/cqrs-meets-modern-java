package ch.martinelli.demo.cqrs.api;

import java.time.LocalDateTime;
import java.util.List;

public record PurchaseOrderDTO(Long id, LocalDateTime orderDate, CustomerDTO customer, List<OrderItemDTO> items) {
}