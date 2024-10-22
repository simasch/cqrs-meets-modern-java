package ch.martinelli.demo.cqrs.command;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("commands/order")
@RestController
class OrderCommandController {

    private final OrderCommandHandler commandHandler;

    OrderCommandController(OrderCommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @PostMapping
    Optional<?> execute(@RequestBody @Valid OrderCommand orderCommand) {
        return commandHandler.handle(orderCommand);
    }

    @ExceptionHandler
    ProblemDetail handle(IllegalArgumentException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.PRECONDITION_FAILED, ex.getMessage());
    }
}
