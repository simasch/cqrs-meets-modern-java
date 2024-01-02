package ch.martinelli.demo.cqrs.api;

import ch.martinelli.demo.cqrs.service.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("customers")
@RestController
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    List<CustomerWithOrders> getCustomers(@RequestParam int offset, @RequestParam int limit) {
        return customerService.findAllByOrdersIsNotEmpty(offset, limit);
    }
}
