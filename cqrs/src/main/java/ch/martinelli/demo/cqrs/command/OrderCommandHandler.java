package ch.martinelli.demo.cqrs.command;

import ch.martinelli.demo.cqrs.db.tables.records.OrderItemRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RequestMapping("orders")
@RestController
class OrderCommandHandler {

    private final OrderService orderService;

    public OrderCommandHandler(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    ResponseEntity<?> createOrder(@RequestBody CreateOrderCommand createPurchaseOrder) {
        try {
            var purchaseOrder = orderService.createOrder(createPurchaseOrder.customerId());
            return ResponseEntity.created(fromCurrentRequest().path("/{id}").buildAndExpand(purchaseOrder.getId()).toUri()).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/items")
    ResponseEntity<?> addItem(@RequestBody AddOrderItemCommand addOrderItem) {
        try {
            OrderItemRecord orderItemRecord = orderService.addItem(addOrderItem.orderId(), addOrderItem.productId(), addOrderItem.quantity());
            return ResponseEntity.created(fromCurrentRequest().path("/{id}").buildAndExpand(orderItemRecord.getId()).toUri()).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
