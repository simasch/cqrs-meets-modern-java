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
class OrderController {

    private final OrderCommandHandler commandHandler;

    OrderController(OrderCommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @PostMapping
    ResponseEntity<?> createOrder(@RequestBody @Valid OrderCommand.CreateOrderCommand createOrderCommand) {

        var purchaseOrderId = commandHandler.handle(createOrderCommand).orElse(null);

        return created(fromCurrentRequest().path("/{id}").buildAndExpand(purchaseOrderId).toUri()).build();
    }

    @PostMapping("{orderId}/items")
    ResponseEntity<?> addItem(@PathVariable long orderId,
                              @RequestBody @Valid OrderCommand.AddOrderItemCommand addOrderItemCommand) {

        if (orderId != addOrderItemCommand.orderId()) throw new IllegalArgumentException();

        var orderItemId = commandHandler.handle(addOrderItemCommand).orElse(null);

        return created(fromCurrentRequest().path("/{id}").buildAndExpand(orderItemId).toUri()).build();
    }

    @PatchMapping("{orderId}/items/{orderItemId}")
    ResponseEntity<?> updateQuantity(@PathVariable long orderId, @PathVariable long orderItemId,
                                     @Valid @RequestBody OrderCommand.UpdateQuantityCommand updateQuantityCommand) {

        if (orderItemId != updateQuantityCommand.orderItemId()) throw new IllegalArgumentException();

        commandHandler.handle(updateQuantityCommand);

        return ok().build();
    }

    @ExceptionHandler
    public ProblemDetail handle(IllegalArgumentException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.PRECONDITION_FAILED, ex.getMessage());
    }
}
