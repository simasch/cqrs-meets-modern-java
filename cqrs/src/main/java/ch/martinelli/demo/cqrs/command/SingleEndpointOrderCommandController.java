package ch.martinelli.demo.cqrs.command;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("orders/commands")
@RestController
class SingleEndpointOrderCommandController {

    private final OrderCommandHandler commandHandler;

    SingleEndpointOrderCommandController(OrderCommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @PostMapping
    Optional<?> createOrder(@RequestBody @Valid OrderCommand orderCommand) {
        return commandHandler.handle(orderCommand);
    }

    @ExceptionHandler
    public ProblemDetail handle(IllegalArgumentException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.PRECONDITION_FAILED, ex.getMessage());
    }
}
