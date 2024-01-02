package ch.martinelli.demo.cqrs.repository;

import ch.martinelli.demo.cqrs.api.CustomerDTO;
import ch.martinelli.demo.cqrs.api.OrderItemDTO;
import ch.martinelli.demo.cqrs.api.ProductDTO;
import ch.martinelli.demo.cqrs.api.PurchaseOrderDTO;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static ch.martinelli.demo.cqrs.db.tables.Customer.CUSTOMER;
import static ch.martinelli.demo.cqrs.db.tables.OrderItem.ORDER_ITEM;
import static ch.martinelli.demo.cqrs.db.tables.PurchaseOrder.PURCHASE_ORDER;
import static org.jooq.Records.mapping;
import static org.jooq.impl.DSL.*;

@Repository
public class CustomerRepository {

    private final DSLContext ctx;

    public CustomerRepository(DSLContext ctx) {
        this.ctx = ctx;
    }

    public List<CustomerDTO> findAllByOrdersIsNotEmpty(int offset, int limit) {
        return ctx.select(CUSTOMER.ID,
                        CUSTOMER.FIRST_NAME,
                        CUSTOMER.LAST_NAME,
                        CUSTOMER.STREET,
                        CUSTOMER.POSTAL_CODE,
                        CUSTOMER.CITY,
                        multiset(
                                select(PURCHASE_ORDER.ID,
                                        PURCHASE_ORDER.ORDER_DATE,
                                        multiset(
                                                select(ORDER_ITEM.ID,
                                                        ORDER_ITEM.QUANTITY,
                                                        row(ORDER_ITEM.product().ID,
                                                                ORDER_ITEM.product().NAME,
                                                                ORDER_ITEM.product().PRICE
                                                        ).mapping(ProductDTO::new))
                                                        .from(ORDER_ITEM)
                                                        .where(ORDER_ITEM.PURCHASE_ORDER_ID.eq(PURCHASE_ORDER.ID))
                                                        .orderBy(ORDER_ITEM.ID)
                                        ).convertFrom(r -> r.map(mapping(OrderItemDTO::new))))
                                        .from(PURCHASE_ORDER)
                                        .where(PURCHASE_ORDER.ID.eq(CUSTOMER.ID))
                                        .orderBy(PURCHASE_ORDER.ORDER_DATE)
                        ).convertFrom(r -> r.map(mapping(PurchaseOrderDTO::new)))
                ).from(CUSTOMER)
                .orderBy(CUSTOMER.ID)
                .offset(offset)
                .limit(limit)
                .fetch(mapping(CustomerDTO::new));
    }
}
