package ch.martinelli.demo.cqrs.command;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/orders")
@RestController
class OrderController {

    private final OrderCommandHandler commandHandler;

    OrderController(OrderCommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @PostMapping
    ResponseEntity<?> createOrder(@RequestBody OrderCommand.CreateOrder createOrder) {
        return commandHandler.handle(createOrder);
    }

    @PostMapping("/items")
    ResponseEntity<?> addItem(@RequestBody OrderCommand.AddOrderItem addOrderItem) {
        return commandHandler.handle(addOrderItem);
    }

    @PatchMapping("/items/{id}")
    ResponseEntity<?> updateQuantity(@PathVariable long id, @RequestBody OrderCommand.UpdateQuantity updateQuantity) {
        return commandHandler.handle(updateQuantity);
    }

}
