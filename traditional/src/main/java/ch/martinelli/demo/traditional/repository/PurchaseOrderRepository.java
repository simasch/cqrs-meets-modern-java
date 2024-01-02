package ch.martinelli.demo.traditional.repository;

import ch.martinelli.demo.traditional.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
}
