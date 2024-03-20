package ch.martinelli.demo.cqrs.command;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RequestMapping("/orders")
@RestController
class OrderController {

    private final OrderCommandHandler commandHandler;

    OrderController(OrderCommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @PostMapping
    ResponseEntity<?> createOrder(@RequestBody OrderCommand.CreateOrder createOrder) {
        var purchaseOrderId = commandHandler.handle(createOrder).orElse(null);
        return created(fromCurrentRequest().path("/{id}").buildAndExpand(purchaseOrderId).toUri()).build();
    }

    @PostMapping("/items")
    ResponseEntity<?> addItem(@RequestBody OrderCommand.AddOrderItem addOrderItem) {
        var orderItemId = commandHandler.handle(addOrderItem).orElse(null);
        return created(fromCurrentRequest().path("/{id}").buildAndExpand(orderItemId).toUri()).build();
    }

    @PatchMapping("/items/{id}")
    ResponseEntity<?> updateQuantity(@PathVariable long id, @RequestBody OrderCommand.UpdateQuantity updateQuantity) {
        commandHandler.handle(updateQuantity);
        return ok().build();
    }

    @ExceptionHandler
    public ProblemDetail handle(IllegalArgumentException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.PRECONDITION_FAILED, ex.getMessage());
    }
}
