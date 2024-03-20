package ch.martinelli.demo.cqrs.command;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RequestMapping("/orders")
@RestController
class OrderCommandController {

    private final OrderCommandHandler commandHandler;

    OrderCommandController(OrderCommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @PostMapping
    ResponseEntity<?> createOrder(@RequestBody @Valid OrderCommand.CreateOrder createOrder) {
        var purchaseOrderId = commandHandler.handle(createOrder).orElse(null);
        return created(fromCurrentRequest().path("/{id}").buildAndExpand(purchaseOrderId).toUri()).build();
    }

    @PostMapping("{orderId}/items")
    ResponseEntity<?> addItem(@PathVariable long orderId,
                              @RequestBody @Valid OrderCommand.AddOrderItem addOrderItem) {
        if (orderId != addOrderItem.orderId()) {
            throw new IllegalArgumentException();
        }
        var orderItemId = commandHandler.handle(addOrderItem).orElse(null);
        return created(fromCurrentRequest().path("/{id}").buildAndExpand(orderItemId).toUri()).build();
    }

    @PatchMapping("{orderId}/items/{orderItemId}")
    ResponseEntity<?> updateQuantity(@PathVariable long orderId, @PathVariable long orderItemId,
                                     @Valid @RequestBody OrderCommand.UpdateQuantity updateQuantity) {
        if (orderItemId != updateQuantity.orderItemId()) {
            throw new IllegalArgumentException();
        }
        commandHandler.handle(updateQuantity);
        return ok().build();
    }

    @ExceptionHandler
    public ProblemDetail handle(IllegalArgumentException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.PRECONDITION_FAILED, ex.getMessage());
    }
}
