package ch.martinelli.demo.traditional.api;

import ch.martinelli.demo.traditional.entity.PurchaseOrder;
import ch.martinelli.demo.traditional.repository.CustomerRepository;
import ch.martinelli.demo.traditional.repository.PurchaseOrderRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("orders")
@RestController
public class OrderController {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    public OrderController(PurchaseOrderRepository purchaseOrderRepository,
                           CustomerRepository customerRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.customerRepository = customerRepository;
        this.modelMapper = new ModelMapper();
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void post(@RequestBody PurchaseOrderDTO purchaseOrderDTO) {
        var purchaseOrder = modelMapper.map(purchaseOrderDTO, PurchaseOrder.class);

        customerRepository.findById(purchaseOrder.getCustomer().getId())
                .ifPresent(purchaseOrder::setCustomer);

        purchaseOrderRepository.save(purchaseOrder);
    }

    @PutMapping("{id}")
    void put(@PathVariable Long id, @RequestBody PurchaseOrderDTO purchaseOrderDTO) {
        if (id.equals(purchaseOrderDTO.getId())) throw new IllegalArgumentException();

        var purchaseOrder = modelMapper.map(purchaseOrderDTO, PurchaseOrder.class);

        purchaseOrderRepository.save(purchaseOrder);
    }

    @GetMapping
    List<PurchaseOrderDTO> getPurchaseOrders(@RequestParam String firstName,
                                             @RequestParam String lastName,
                                             @RequestParam int pageNumber,
                                             @RequestParam int pageSize) {
        var purchaseOrders = purchaseOrderRepository
                .findAllByCustomerFirstNameIgnoreCaseLikeOrCustomerLastNameIgnoreCaseLike(
                        firstName, lastName,
                        PageRequest.of(pageNumber, pageSize, Sort.by("orderDate")));

        return purchaseOrders.stream()
                .map(order -> modelMapper.map(order, PurchaseOrderDTO.class))
                .toList();
    }
}
