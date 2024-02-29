package ch.martinelli.demo.cqrs.query;

public record CustomerWithOrdersQuery(int offset, int limit) {
}
