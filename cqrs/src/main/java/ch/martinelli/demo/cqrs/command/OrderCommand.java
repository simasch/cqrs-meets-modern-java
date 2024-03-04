package ch.martinelli.demo.cqrs.command;

sealed interface OrderCommand {

    record CreateOrder(long customerId) implements OrderCommand {
    }

    record AddOrderItem(long orderId, long productId, int quantity) implements OrderCommand {
    }

    record UpdateQuantity(long orderItemId, int quantity) implements OrderCommand{
    }
}
