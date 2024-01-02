package ch.martinelli.demo.cqrs.api;

import ch.martinelli.demo.cqrs.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RequestMapping("orders")
@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    ResponseEntity<?> createPurchaseOrder(@RequestBody CreatePurchaseOrder createPurchaseOrder) {
        try {
            var purchaseOrder = orderService.placeOrder(createPurchaseOrder);
            return ResponseEntity.created(fromCurrentRequest().path("/{id}").buildAndExpand(purchaseOrder.getId()).toUri()).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
