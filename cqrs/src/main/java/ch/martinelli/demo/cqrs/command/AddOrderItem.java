package ch.martinelli.demo.cqrs.command;

record AddOrderItem(long orderId, long productId, int quantity) {
}
