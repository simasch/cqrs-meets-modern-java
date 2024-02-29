package ch.martinelli.demo.cqrs.query;

import java.util.List;

record CustomerWithOrders(Long id, String firstName, String lastName, String street, String postalCode, String city,
                          List<PurchaseOrder> orders) {
}
