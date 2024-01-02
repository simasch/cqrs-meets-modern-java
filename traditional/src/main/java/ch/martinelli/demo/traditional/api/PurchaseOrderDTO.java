package ch.martinelli.demo.traditional.api;

import java.time.LocalDateTime;
import java.util.List;

public class PurchaseOrderDTO {

    private Long id;
    private LocalDateTime orderDate;
    private CustomerDTO customer;
    private List<OrderItemDTO> items;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomerDTO(CustomerDTO customer) {
        this.customer = customer;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }
}
