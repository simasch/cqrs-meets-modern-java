package ch.martinelli.demo.cqrs.query;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("orders")
@RestController
class OrderQueryController {

    private final OrderRepository orderRepository;

    OrderQueryController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping
    List<PurchaseOrder> getCustomersWithOrders(FindOrders query) {
        return orderRepository.findOrders(query.offset(), query.limit());
    }
}
