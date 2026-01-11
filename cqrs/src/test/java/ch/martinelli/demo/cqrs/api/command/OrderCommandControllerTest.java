package ch.martinelli.demo.cqrs.api.command;

import ch.martinelli.demo.cqrs.TestCqrsApplication;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StopWatch;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestCqrsApplication.class)
@SpringBootTest
@AutoConfigureMockMvc
class OrderCommandControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderCommandControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createPurchaseOrderWithOneItem() throws Exception {
        var stopWatch = new StopWatch();
        stopWatch.start();

        var createOrderResult = mockMvc.perform(post("/commands/order")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                    "@type": "CreateOrderCommand",
                                    "customerId":  1
                                }"""))
                .andExpect(status().isOk())
                .andReturn();

        stopWatch.stop();
        LOGGER.info("Create order took {} ms", stopWatch.getTotalTimeMillis());

        stopWatch.start();

        var addOrderItemResult = mockMvc.perform(post("/commands/order")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                    "@type": "AddOrderItemCommand",
                                    "orderId": %s,
                                    "productId": 1,
                                    "quantity": 1
                                }""".formatted(createOrderResult.getResponse().getContentAsString())))
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.perform(post("/commands/order")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                    "@type": "UpdateQuantityCommand",
                                    "orderItemId": %s,
                                    "quantity": 1
                                }""".formatted(addOrderItemResult.getResponse().getContentAsString())))
                .andExpect(status().isOk());

        stopWatch.stop();
        LOGGER.info("Add order item took {} ms", stopWatch.getTotalTimeMillis());
    }
}