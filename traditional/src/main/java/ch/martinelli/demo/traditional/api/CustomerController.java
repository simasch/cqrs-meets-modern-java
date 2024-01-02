package ch.martinelli.demo.traditional.api;

import ch.martinelli.demo.traditional.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("customers")
@RestController
public class CustomerController {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        modelMapper = new ModelMapper();
    }

    @GetMapping
    List<CustomerWithOrdersDTO> getCustomers(@RequestParam int pageNumber, @RequestParam int pageSize) {
        var customers = customerRepository.findAllByOrdersIsNotEmpty(PageRequest.of(pageNumber, pageSize));

        return customers.stream().map(c -> modelMapper.map(c, CustomerWithOrdersDTO.class)).toList();
    }
}
