package ch.martinelli.demo.traditional.repository;

import ch.martinelli.demo.traditional.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
