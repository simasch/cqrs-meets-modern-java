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
import org.springframework.util.StopWatch;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
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

        mockMvc.perform(post("/orders")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                    "customerId":  1
                                }"""))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "http://localhost/orders/100000"))
                .andReturn();

        stopWatch.stop();
        LOGGER.info("Create order took {} ms", stopWatch.getTotalTimeMillis());

        stopWatch.start();

        mockMvc.perform(post("/orders/100000/items")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                    "orderId": 100000,
                                    "productId": 1,
                                    "quantity": 1
                                }"""))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "http://localhost/orders/100000/items/100000"));

        mockMvc.perform(patch("/orders/100000/items/100000")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                    "orderItemId": 100000,
                                    "quantity": 1
                                }"""))
                .andExpect(status().isOk());

        stopWatch.stop();
        LOGGER.info("Add order item took {} ms", stopWatch.getTotalTimeMillis());
    }
}