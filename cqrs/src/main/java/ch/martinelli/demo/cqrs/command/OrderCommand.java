package ch.martinelli.demo.cqrs.command;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

sealed interface OrderCommand {

    record CreateOrder(@Min(1) long customerId) implements OrderCommand {
    }

    record AddOrderItem(@Min(1) long orderId,
                        @Min(1) long productId,
                        @Min(1) @Max(10) int quantity) implements OrderCommand {
    }

    record UpdateQuantity(@Min(1) long orderItemId,
                          @Min(1) @Max(10) int quantity) implements OrderCommand {
    }
}
