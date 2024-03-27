package ch.martinelli.demo.cqrs.api.query;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestCqrsApplication.class)
@SpringBootTest
@AutoConfigureMockMvc
class OrderQueryControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderQueryControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getOrders() throws Exception {
        var stopWatch = new StopWatch();
        stopWatch.start();

        mockMvc.perform(get("/orders?firstName=%&lastName=%&offset=0&limit=500"))
                .andExpect(status().isOk());

        stopWatch.stop();
        LOGGER.info("Test took {} ms", stopWatch.getTotalTimeMillis());
    }
}