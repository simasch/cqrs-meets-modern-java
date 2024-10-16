package ch.martinelli.demo.cqrs.api.command;

import ch.martinelli.demo.cqrs.TestCqrsApplication;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.StopWatch;

import java.util.Objects;
import java.util.regex.Pattern;

import static org.hamcrest.Matchers.startsWith;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestCqrsApplication.class)
@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createPurchaseOrderWithOneItem() throws Exception {
        var stopWatch = new StopWatch();
        stopWatch.start();

        MvcResult postOrderResult = mockMvc.perform(post("/orders")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                    "@type": "CreateOrderCommand",
                                    "customerId":  1
                                }"""))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", startsWith("http://localhost/orders/")))
                .andReturn();

        var orderId = getId(postOrderResult);

        stopWatch.stop();
        LOGGER.info("Create order took {} ms", stopWatch.getTotalTimeMillis());

        stopWatch.start();

        var addOrderItemResult = mockMvc.perform(post("/orders/%s/items".formatted(orderId))
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                    "@type": "AddOrderItemCommand",
                                    "orderId": %s,
                                    "productId": 1,
                                    "quantity": 1
                                }""".formatted(orderId)))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", startsWith("http://localhost/orders/%s/items/".formatted(orderId))))
                .andReturn();

        var orderItemId = getId(addOrderItemResult);

        mockMvc.perform(patch("/orders/%s/items/%s".formatted(orderId, orderItemId))
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                    "@type": "UpdateQuantityCommand",
                                    "orderItemId": %s,
                                    "quantity": 1
                                }""".formatted(orderItemId)))
                .andExpect(status().isOk());

        stopWatch.stop();
        LOGGER.info("Add order item took {} ms", stopWatch.getTotalTimeMillis());
    }

    private long getId(MvcResult mvcResult) {
        var location = mvcResult.getResponse().getHeader("location");
        var pattern = Pattern.compile("(\\d+)$");
        var matcher = pattern.matcher(Objects.requireNonNull(location));
        boolean found = matcher.find();
        return found ? Long.parseLong(matcher.group(), 10) : 0L;
    }
}