package ch.martinelli.demo.traditional.repository;

import ch.martinelli.demo.traditional.entity.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findAllByOrdersIsNotEmpty(Pageable pageable);
}
