package ch.martinelli.demo.cqrs.command;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
class OrderNotFoundException extends RuntimeException {
}
