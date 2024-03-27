package ch.martinelli.demo.cqrs.query;

public record FindOrders(String firstName, String lastName, int offset, int limit) {
}
