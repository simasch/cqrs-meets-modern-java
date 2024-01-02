package ch.martinelli.demo.cqrs.api;

import java.util.List;

public record CustomerWithOrdersDTO(Long id, String firstName, String lastName, String street, String postalCode, String city,
                                    List<PurchaseOrderDTO> orders) {
}
