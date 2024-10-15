package ch.martinelli.demo.cqrs.command;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.SIMPLE_NAME
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = OrderCommand.CreateOrderCommand.class),
        @JsonSubTypes.Type(value = OrderCommand.AddOrderItemCommand.class),
        @JsonSubTypes.Type(value = OrderCommand.UpdateQuantityCommand.class),
})
sealed interface OrderCommand {

    record CreateOrderCommand(
            @Min(1) long customerId) implements OrderCommand {
    }

    record AddOrderItemCommand(
            @Min(1) long orderId,
            @Min(1) long productId,
            @Min(1) @Max(10) int quantity) implements OrderCommand {
    }

    record UpdateQuantityCommand(
            @Min(1) long orderItemId,
            @Min(1) @Max(10) int quantity) implements OrderCommand {
    }
}
