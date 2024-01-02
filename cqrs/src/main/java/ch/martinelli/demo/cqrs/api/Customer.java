package ch.martinelli.demo.cqrs.api;

public record Customer(Long id, String firstName, String lastName, String street, String postalCode, String city) {
}
