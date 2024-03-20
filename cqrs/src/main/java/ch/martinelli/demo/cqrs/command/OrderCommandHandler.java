package ch.martinelli.demo.cqrs.command;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class OrderCommandHandler {

    private final OrderService orderService;

    OrderCommandHandler(OrderService orderService) {
        this.orderService = orderService;
    }

    Optional<?> handle(OrderCommand orderCommand) {
        switch (orderCommand) {
            case OrderCommand.CreateOrder(long customerId) -> {
                var purchaseOrder = orderService.createOrder(customerId);
                return Optional.of(purchaseOrder.getId());
            }
            case OrderCommand.AddOrderItem(long orderId, long productId, int quantity) -> {
                var orderItemRecord = orderService.addItem(orderId, productId, quantity);
                return Optional.of(orderItemRecord.getId());
            }
            case OrderCommand.UpdateQuantity(long orderItemId, int quantity) -> {
                orderService.updateQuantity(orderItemId, quantity);
                return Optional.empty();
            }
        }
    }
}
