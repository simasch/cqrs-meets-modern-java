package ch.martinelli.demo.traditional.api;

import ch.martinelli.demo.traditional.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
    }

    @GetMapping
    List<CustomerWithOrdersDTO> getCustomersWithOrders(@RequestParam int pageNumber, @RequestParam int pageSize) {
        var customers = customerRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by("id")));

        return customers.stream().map(c -> modelMapper.map(c, CustomerWithOrdersDTO.class)).toList();
    }
}
