package ch.martinelli.demo.cqrs.query;

record FindOrders(String firstName, String lastName, int offset, int limit) {
}
