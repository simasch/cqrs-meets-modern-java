package ch.martinelli.demo.cqrs.api;

import java.util.List;

public record CustomerWithOrders(Long id, String firstName, String lastName, String street, String postalCode, String city,
                                 List<PurchaseOrder> orders) {
}
