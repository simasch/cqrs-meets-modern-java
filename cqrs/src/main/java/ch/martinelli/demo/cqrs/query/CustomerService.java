package ch.martinelli.demo.cqrs.query;

import org.jooq.DSLContext;
import org.jooq.Records;
import org.springframework.stereotype.Service;

import java.util.List;

import static ch.martinelli.demo.cqrs.db.tables.Customer.CUSTOMER;
import static ch.martinelli.demo.cqrs.db.tables.OrderItem.ORDER_ITEM;
import static ch.martinelli.demo.cqrs.db.tables.PurchaseOrder.PURCHASE_ORDER;
import static org.jooq.Records.mapping;
import static org.jooq.impl.DSL.*;

@Service
class CustomerService {

    private final DSLContext ctx;

    CustomerService(DSLContext ctx) {
        this.ctx = ctx;
    }

    List<CustomerWithOrders> findAllByOrdersIsNotEmpty(int offset, int limit) {
        return ctx.select(CUSTOMER.ID,
                        CUSTOMER.FIRST_NAME,
                        CUSTOMER.LAST_NAME,
                        CUSTOMER.STREET,
                        CUSTOMER.POSTAL_CODE,
                        CUSTOMER.CITY,
                        multiset(
                                select(PURCHASE_ORDER.ID,
                                        PURCHASE_ORDER.ORDER_DATE,
                                        row(PURCHASE_ORDER.customer().ID,
                                                PURCHASE_ORDER.customer().FIRST_NAME, PURCHASE_ORDER.customer().LAST_NAME,
                                                PURCHASE_ORDER.customer().STREET, PURCHASE_ORDER.customer().POSTAL_CODE,
                                                PURCHASE_ORDER.customer().CITY).mapping(Customer::new),
                                        multiset(
                                                select(ORDER_ITEM.ID,
                                                        ORDER_ITEM.QUANTITY,
                                                        row(ORDER_ITEM.product().ID,
                                                                ORDER_ITEM.product().NAME,
                                                                ORDER_ITEM.product().PRICE
                                                        ).mapping(Product::new))
                                                        .from(ORDER_ITEM)
                                                        .where(ORDER_ITEM.PURCHASE_ORDER_ID.eq(PURCHASE_ORDER.ID))
                                                        .orderBy(ORDER_ITEM.ID)
                                        ).convertFrom(r -> r.map(Records.mapping(OrderItem::new))))
                                        .from(PURCHASE_ORDER)
                                        .where(PURCHASE_ORDER.customer().ID.eq(CUSTOMER.ID))
                                        .orderBy(PURCHASE_ORDER.ORDER_DATE)
                        ).convertFrom(r -> r.map(Records.mapping(PurchaseOrder::new)))
                ).from(CUSTOMER)
                .orderBy(CUSTOMER.ID)
                .offset(offset)
                .limit(limit)
                .fetch(mapping(CustomerWithOrders::new));
    }
}
