package ch.martinelli.demo.traditional.repository;

import ch.martinelli.demo.traditional.TestTraditionalApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;

@Import(TestTraditionalApplication.class)
@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void findAllByOrdersIsNotEmpty() {
        var customers = customerRepository.findAllByOrdersIsNotEmpty(PageRequest.of(0, 100));

        assertThat(customers).hasSize(100);
    }
}