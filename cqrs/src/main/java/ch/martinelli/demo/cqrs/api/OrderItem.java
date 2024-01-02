package ch.martinelli.demo.cqrs.api;

public record OrderItem(Long id, int quantity, Product product) {
}