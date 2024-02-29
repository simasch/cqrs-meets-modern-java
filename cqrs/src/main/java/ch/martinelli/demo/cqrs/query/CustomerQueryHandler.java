package ch.martinelli.demo.cqrs.query;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("customers")
@RestController
class CustomerQueryHandler {

    private final CustomerService customerService;

    CustomerQueryHandler(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    List<CustomerWithOrders> getCustomersWithOrders(CustomerWithOrdersQuery query) {
        return customerService.findAllByOrdersIsNotEmpty(query.offset(), query.limit());
    }
}
