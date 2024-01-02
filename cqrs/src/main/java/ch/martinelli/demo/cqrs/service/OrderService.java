package ch.martinelli.demo.cqrs.service;

import ch.martinelli.demo.cqrs.api.CreatePurchaseOrder;
import ch.martinelli.demo.cqrs.db.tables.records.PurchaseOrderRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static ch.martinelli.demo.cqrs.db.tables.Customer.CUSTOMER;
import static ch.martinelli.demo.cqrs.db.tables.PurchaseOrder.PURCHASE_ORDER;

@Service
public class OrderService {

    private final DSLContext ctx;

    public OrderService(DSLContext ctx) {
        this.ctx = ctx;
    }

    @Transactional
    public PurchaseOrderRecord placeOrder(CreatePurchaseOrder createPurchaseOrder) {
        var customer = ctx.selectFrom(CUSTOMER)
                .where(CUSTOMER.ID.eq(createPurchaseOrder.customerId()))
                .fetchOptional()
                .orElseThrow();

        var purchaseOrder = ctx.newRecord(PURCHASE_ORDER);
        purchaseOrder.setOrderDate(LocalDateTime.now());
        purchaseOrder.setCustomerId(customer.getId());

        purchaseOrder.store();

        return purchaseOrder;
    }
}
