package ch.martinelli.demo.traditional.repository;

import ch.martinelli.demo.traditional.entity.PurchaseOrder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {

    List<PurchaseOrder> findAllByCustomerFirstNameIgnoreCaseLikeOrCustomerLastNameIgnoreCaseLike(String firstName, String lastName, Pageable pageable);
}
