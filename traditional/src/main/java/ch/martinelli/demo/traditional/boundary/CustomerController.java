package ch.martinelli.demo.traditional.boundary;

import ch.martinelli.demo.traditional.control.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
    List<CustomerDTO> getCustomers() {
        var customers = customerRepository.findAllByOrdersIsNotEmpty();

        var dtos = customers.stream().map(c -> modelMapper.map(c, CustomerDTO.class)).toList();
        return dtos;
    }
}
