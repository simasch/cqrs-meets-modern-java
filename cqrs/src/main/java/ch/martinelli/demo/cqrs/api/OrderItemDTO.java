package ch.martinelli.demo.cqrs.api;

public record OrderItemDTO (Long id, int quantity, ProductDTO product) {
}