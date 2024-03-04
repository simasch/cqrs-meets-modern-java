package ch.martinelli.demo.cqrs.command;

import ch.martinelli.demo.cqrs.db.tables.records.OrderItemRecord;
import ch.martinelli.demo.cqrs.db.tables.records.PurchaseOrderRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static ch.martinelli.demo.cqrs.db.tables.Customer.CUSTOMER;
import static ch.martinelli.demo.cqrs.db.tables.OrderItem.ORDER_ITEM;
import static ch.martinelli.demo.cqrs.db.tables.Product.PRODUCT;
import static ch.martinelli.demo.cqrs.db.tables.PurchaseOrder.PURCHASE_ORDER;

@Service
class OrderService {

    private final DSLContext ctx;

    OrderService(DSLContext ctx) {
        this.ctx = ctx;
    }

    @Transactional
    PurchaseOrderRecord createOrder(long customerId) {
        var customer = ctx.selectFrom(CUSTOMER)
                .where(CUSTOMER.ID.eq(customerId))
                .fetchOptional()
                .orElseThrow(CustomerNotFoundException::new);

        var purchaseOrder = ctx.newRecord(PURCHASE_ORDER);
        purchaseOrder.setOrderDate(LocalDateTime.now());
        purchaseOrder.setCustomerId(customer.getId());

        purchaseOrder.store();

        return purchaseOrder;
    }

    OrderItemRecord addItem(long purchaseOrderId, long productId, int quantity) {
        if (!ctx.fetchExists(ctx.selectFrom(PURCHASE_ORDER).where(PURCHASE_ORDER.ID.eq(purchaseOrderId)))) {
            throw new OrderNotFoundException();
        }

        if (!ctx.fetchExists(ctx.selectFrom(PRODUCT).where(PRODUCT.ID.eq(productId)))) {
            throw new ProductNotFoundException();
        }

        var orderItem = ctx.newRecord(ORDER_ITEM);
        orderItem.setPurchaseOrderId(purchaseOrderId);
        orderItem.setProductId(productId);
        orderItem.setQuantity(quantity);
        orderItem.store();

        return orderItem;
    }

    void updateQuantity(long orderItemId, int quantity) {
        int numRowsUpdated = ctx.update(ORDER_ITEM)
                .set(ORDER_ITEM.QUANTITY, quantity)
                .where(ORDER_ITEM.ID.eq(orderItemId))
                .execute();
        if (numRowsUpdated == 0) {
            throw new OrderItemNotFoundException();
        }
    }
}
