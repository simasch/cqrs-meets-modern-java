package ch.martinelli.demo.cqrs.command;

record AddOrderItemCommand(long orderId, long productId, int quantity) {
}
