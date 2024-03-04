package ch.martinelli.demo.cqrs.command;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@Component
class OrderCommandHandler {

    private final OrderService orderService;

    OrderCommandHandler(OrderService orderService) {
        this.orderService = orderService;
    }

    ResponseEntity<?> handle(OrderCommand orderCommand) {
        switch (orderCommand) {
            case OrderCommand.CreateOrder(long customerId) -> {
                var purchaseOrder = orderService.createOrder(customerId);
                return created(fromCurrentRequest().path("/{id}").buildAndExpand(purchaseOrder.getId()).toUri()).build();
            }
            case OrderCommand.AddOrderItem(long orderId, long productId, int quantity) -> {
                var orderItemRecord = orderService.addItem(orderId, productId, quantity);
                return created(fromCurrentRequest().path("/{id}").buildAndExpand(orderItemRecord.getId()).toUri()).build();
            }
            case OrderCommand.UpdateQuantity(long orderItemId, int quantity) -> {
                orderService.updateQuantity(orderItemId, quantity);
                return ok().build();
            }
        }
    }
}
