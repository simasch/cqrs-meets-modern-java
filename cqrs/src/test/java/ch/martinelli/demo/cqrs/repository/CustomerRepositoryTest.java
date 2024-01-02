package ch.martinelli.demo.cqrs.repository;

import ch.martinelli.demo.cqrs.TestCqrsApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@Import(TestCqrsApplication.class)
@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void findAllByOrdersIsNotEmpty() {
        var customers = customerRepository.findAllByOrdersIsNotEmpty(0, 100);

        assertThat(customers).hasSize(100);
    }
}