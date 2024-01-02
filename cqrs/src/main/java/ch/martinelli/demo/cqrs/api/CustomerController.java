package ch.martinelli.demo.cqrs.api;

import ch.martinelli.demo.cqrs.repository.CustomerRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("customers")
@RestController
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping
    List<CustomerWithOrdersDTO> getCustomers(@RequestParam int offset, @RequestParam int limit) {
        return customerRepository.findAllByOrdersIsNotEmpty(offset, limit);
    }
}
