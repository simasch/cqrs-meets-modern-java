package ch.martinelli.demo.traditional.boundary;

import ch.martinelli.demo.traditional.entity.PurchaseOrder;

import java.util.List;

public class CustomerDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private List<PurchaseOrderDTO> orders;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<PurchaseOrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<PurchaseOrderDTO> orders) {
        this.orders = orders;
    }
}
