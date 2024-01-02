package ch.martinelli.demo.traditional.api;

import java.util.List;

public class CustomerWithOrdersDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String street;
    private String postalCode;
    private String city;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<PurchaseOrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<PurchaseOrderDTO> orders) {
        this.orders = orders;
    }
}
